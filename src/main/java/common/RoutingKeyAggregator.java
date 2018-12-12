package common;

import com.hazelcast.aggregation.Aggregator;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoutingKeyAggregator extends Aggregator<Map.Entry<String, SessionData>, Collection<String>> implements Serializable {

    Set<String> sessionData = new HashSet<>();
    private final int punterId;

    public RoutingKeyAggregator(int punterId) {
        this.punterId = punterId;
    }

    @Override
    public void accumulate(Map.Entry<String, SessionData> input) {
         if ((Integer)input.getValue().get(SessionData.PUNTER_ID_KEY) == punterId) {
             sessionData.add(input.getValue().get(SessionData.ROUTING_KEY).toString());
         }
    }

    @Override
    public void combine(Aggregator aggregator) {
        sessionData.addAll(this.getClass().cast(aggregator).sessionData);
    }

    @Override
    public Collection<String> aggregate() {
        return sessionData;
    }
}
