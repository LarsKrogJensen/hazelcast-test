package scenarios;

import com.hazelcast.core.IMap;
import common.IQueryProvider;
import common.RoutingKeyAggregator;
import common.SessionData;

import java.util.Collection;

public class AggregatorBase implements IQueryProvider {
    @Override
    public Collection<String> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        return map.aggregate(new RoutingKeyAggregator(punterId));
    }
}
