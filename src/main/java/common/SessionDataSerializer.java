package common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class SessionDataSerializer implements StreamSerializer<SessionData> {
    // Kryo is not thread safe, therefore we pool the resources needed.
    private final Pool<KryoContext> pool = new Pool<>(true, false, 8) {
        @Override
        protected KryoContext create() {
            return new KryoContext();
        }
    };

    public SessionDataSerializer() {
    }

    @Override
    public int getTypeId() {
        return 99090;
    }

    @Override
    public void write(ObjectDataOutput dataOutput, SessionData object) {
        var context = pool.obtain();

        try {
            var kryo = context.kryo;
            var output = context.output;

            output.setOutputStream((OutputStream) dataOutput);
            kryo.writeObject(output, object);
            output.flush();
        } finally {
            pool.free(context);
        }

    }

    @Override
    public SessionData read(ObjectDataInput dataInput) {
        var context = pool.obtain();
        try {
            var kryo = context.kryo;
            var input = context.input;
            input.setInputStream((InputStream) dataInput);
            return kryo.readObject(input, SessionData.class);
        } finally {
            pool.free(context);
        }
    }

    @Override
    public void destroy() {
        pool.clean();
    }

    private static class KryoContext {
        private static final int BUFFER_SIZE = 16 * 1024; // 16 kB
        final Kryo kryo;
        final Output output;
        final Input input;

        KryoContext() {
            kryo = new Kryo();
            kryo.register(SessionData.class);
            kryo.register(SomeData.class);
            kryo.register(OtherData.class);
            kryo.register(HashMap.class);
            // by passes constructors, this improvs perf but assumes no construcutor logic
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            input = new Input(BUFFER_SIZE);
            output = new Output(BUFFER_SIZE, -1); // -1 -> allow grow buffer
        }
    }
}