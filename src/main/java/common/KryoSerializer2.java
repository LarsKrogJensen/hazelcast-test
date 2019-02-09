package common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class KryoSerializer2 implements StreamSerializer<Object> {

    static private final ThreadLocal<KryoContext> kryos = ThreadLocal.withInitial(KryoContext::new);

    @Override
    public int getTypeId() {
        return 123;
    }

    @Override
    public void write(ObjectDataOutput dataOutput, Object object) {
        OutputStream out = (OutputStream) dataOutput;
        KryoContext kryoContext = kryos.get();
        OutputChunked output = kryoContext.output;
        output.setOutputStream(out);
        kryoContext.kryo.writeClassAndObject(output, object);

        output.endChunk();
        output.flush();
    }

    @Override
    public Object read(ObjectDataInput dataInput) {
        InputStream in = (InputStream) dataInput;
        KryoContext kryoContext = kryos.get();
        InputChunked input = kryoContext.input;
        input.setInputStream(in);
        return kryoContext.kryo.readClassAndObject(input);
    }

    @Override
    public void destroy() {
    }

    private static class KryoContext {
        final Kryo kryo;
        final OutputChunked output;
        final InputChunked input;

        KryoContext() {
            int bufferSize = 16 * 1024;
            kryo = new Kryo();
            kryo.register(SessionData.class);
            kryo.register(SomeData.class);
            kryo.register(OtherData.class);
            kryo.register(HashMap.class);
            kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            input = new InputChunked(bufferSize);
            output = new OutputChunked(bufferSize);
        }
    }
}