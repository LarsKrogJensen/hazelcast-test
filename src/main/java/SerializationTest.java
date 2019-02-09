import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import common.OtherData;
import common.SessionData;
import common.SomeData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

public class SerializationTest {

    public static void main(String[] args) throws IOException {
        javaSerialization();
        javaSerializationWithCompression();
        kryoSerialization();
        kryoSerializationCompressed();
    }

    private static void kryoSerialization() throws FileNotFoundException {
        Kryo kryo = new Kryo();
        kryo.register(SessionData.class);
        kryo.register(SomeData.class);
        kryo.register(OtherData.class);
        kryo.register(HashMap.class);

        SessionData sessionData = SessionData.create(1, 2);

        Output output = new Output(new FileOutputStream("kryo.bin"));
        kryo.writeClassAndObject(output, sessionData);
        output.close();

    }

    private static void kryoSerializationCompressed() throws IOException {
        Kryo kryo = new Kryo();
        kryo.register(SessionData.class);
        kryo.register(SomeData.class);
        kryo.register(OtherData.class);
        kryo.register(HashMap.class);

        SessionData sessionData = SessionData.create(1, 2);

        FileOutputStream outputStream = new FileOutputStream("kryo.bin.gz");
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        Output output = new Output(gzipOutputStream);
        kryo.writeClassAndObject(output, sessionData);
        output.close();
        outputStream.close();

    }

    private static void javaSerialization() throws IOException {
        SessionData sessionData = SessionData.create(1, 2);
        FileOutputStream fileOutputStream = new FileOutputStream("java_serialization.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(sessionData);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private static void javaSerializationWithCompression() throws IOException {
        SessionData sessionData = SessionData.create(1, 2);
        FileOutputStream fileOutputStream = new FileOutputStream("java_serialization.bin.gz");
        GZIPOutputStream gzip = new GZIPOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzip);
        objectOutputStream.writeObject(sessionData);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
