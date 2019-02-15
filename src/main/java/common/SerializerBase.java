package common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.util.Pool;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class SerializerBase<T> implements StreamSerializer<T> {
    // Kryo is not thread safe, therefore we pool the resources needed.
    private final Pool<KryoContext> pool = new Pool<>(true, false, 8) {
        @Override
        protected KryoContext create() {
            return new KryoContext();
        }
    };

    private final int typeId;
    private final Class<T> type;

    protected SerializerBase(int typeId, Class<T> type) {
        this.typeId = typeId;
        this.type = type;
    }


    @Override
    public int getTypeId() {
        return typeId;
    }

    @Override
    public void write(ObjectDataOutput dataOutput, T object) {
        var context = pool.obtain();

        try {
            var kryo = context.kryo;
            var output = context.output;
//            System.out.printf("Serializing class %s \n", object.getClass().getSimpleName());
            output.setOutputStream((OutputStream) dataOutput);
            kryo.writeObject(output, object);
            output.endChunk();
            output.flush();
        } finally {
            pool.free(context);
        }

    }

    @Override
    public T read(ObjectDataInput dataInput) {
        var context = pool.obtain();
        try {
            var kryo = context.kryo;
            var input = context.input;
            input.setInputStream((InputStream) dataInput);
//            System.out.printf("Deserializing class %s \n", type.getSimpleName());
            return kryo.readObject(input, type);
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
        final OutputChunked output;
        final InputChunked input;

        KryoContext() {
            kryo = new Kryo();
            kryo.setRegistrationRequired(false);
            // by passes constructors, this improvs perf but assumes no construcutor logic
//            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            input = new InputChunked(BUFFER_SIZE);
            output = new OutputChunked(BUFFER_SIZE); 
        }
    }
}