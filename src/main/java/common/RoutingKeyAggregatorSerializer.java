package common;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

public class RoutingKeyAggregatorSerializer extends SerializerBase<RoutingKeyAggregator> {

    public RoutingKeyAggregatorSerializer() {
        super(124, RoutingKeyAggregator.class);
    }

    @Override
    public void write(ObjectDataOutput dataOutput, RoutingKeyAggregator object) {
        super.write(dataOutput, object);
    }

    @Override
    public RoutingKeyAggregator read(ObjectDataInput dataInput) {
        return super.read(dataInput);
    }
}