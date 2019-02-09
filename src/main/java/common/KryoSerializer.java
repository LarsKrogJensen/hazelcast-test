package common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.util.HashMap;

public class KryoSerializer implements StreamSerializer<Object> {

    private final Pool<Kryo> kryoPool;
    private final Pool<Output> outputPool;
    private final Pool<Input> inputPool;

    public KryoSerializer() {
        kryoPool = new Pool<>(true, false, 8) {
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.register(SessionData.class);
                kryo.register(SomeData.class);
                kryo.register(OtherData.class);
                kryo.register(HashMap.class);

                return kryo;
            }
        };
        outputPool = new Pool<>(true, false, 16) {
            protected Output create() {
                return new Output(1024, -1);
            }
        };
        inputPool = new Pool<>(true, false, 16) {
            protected Input create() {
                return new Input();
            }
        };
    }

    @Override
    public int getTypeId() {
        return 1;
    }

    @Override
    public void write(ObjectDataOutput out, Object object) throws IOException {
        Output output = outputPool.obtain();
        Kryo kryo = kryoPool.obtain();
        try {
            kryo.writeClassAndObject(output, object);
            out.writeByteArray(output.getBuffer());
        } finally {
            kryoPool.free(kryo);
            outputPool.free(output);
        }
    }

    @Override
    public Object read(ObjectDataInput in) throws IOException {
        Input input = inputPool.obtain();
        Kryo kryo = kryoPool.obtain();
        try {
            input.setBuffer(in.readByteArray());
            return kryo.readClassAndObject(input);
        } finally {
            kryoPool.free(kryo);
            inputPool.free(input);
        }
    }

    @Override
    public void destroy() {
    }
}