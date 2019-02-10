import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.internal.serialization.impl.DefaultSerializationServiceBuilder;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.spi.serialization.SerializationService;
import common.OtherData;
import common.SessionData;
import common.SessionDataSerializer;
import common.SomeData;

import java.io.*;
import java.util.HashMap;
import java.util.zip.GZIPOutputStream;

public class SerializationTest {

    public static void main(String[] args) throws IOException {
        javaSerialization();
        javaSerializationWithCompression();
        kryoSerialization();
        kryoSerializationCompressed();
        hazelcastSerialization();
    }

    private static void kryoSerialization() throws FileNotFoundException {
        var kryo = new Kryo();
        kryo.register(SessionData.class);
        kryo.register(SomeData.class);
        kryo.register(OtherData.class);
        kryo.register(HashMap.class);

        var sessionData = SessionData.create(1, 2);

        var output = new Output(new FileOutputStream("kryo.bin"));
        kryo.writeClassAndObject(output, sessionData);
        output.close();

    }

    private static void kryoSerializationCompressed() throws IOException {
        var kryo = new Kryo();
        kryo.register(SessionData.class);
        kryo.register(SomeData.class);
        kryo.register(OtherData.class);
        kryo.register(HashMap.class);

        var sessionData = SessionData.create(1, 2);

        var outputStream = new FileOutputStream("kryo.bin.gz");
        var gzipOutputStream = new GZIPOutputStream(outputStream);
        var output = new Output(gzipOutputStream);
        kryo.writeClassAndObject(output, sessionData);
        output.close();
        outputStream.close();

    }

    private static void javaSerialization() throws IOException {
        var sessionData = SessionData.create(1, 2);
        var fileOutputStream = new FileOutputStream("java_serialization.bin");
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(sessionData);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private static void javaSerializationWithCompression() throws IOException {
        var sessionData = SessionData.create(1, 2);
        var fileOutputStream = new FileOutputStream("java_serialization.bin.gz");
        var gzip = new GZIPOutputStream(fileOutputStream);
        var objectOutputStream = new ObjectOutputStream(gzip);
        objectOutputStream.writeObject(sessionData);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    private static void hazelcastSerialization() throws IOException {
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setTypeClass(SessionData.class);
        serializerConfig.setImplementation(new SessionDataSerializer());

        SerializationConfig serializationConfig = new SerializationConfig();
//        serializationConfig.addSerializerConfig(serializerConfig);

        SessionData sessionData = SessionData.create(1, 2);
        SerializationService ss = new DefaultSerializationServiceBuilder().setConfig(serializationConfig).build();
        Data data = ss.toData(sessionData);
        byte[] bytes = data.toByteArray();
        var fileOutputStream = new FileOutputStream("hazelcast_serialization.bin");
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
