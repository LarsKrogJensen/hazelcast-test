package scenarios;

import com.hazelcast.core.IMap;
import common.IQueryProvider;
import common.SessionData;

import java.util.Collection;

import static common.SessionData.PUNTER_ID_KEY;
import static common.SessionData.ROUTING_KEY_KEY;
import static java.util.stream.Collectors.toList;

public class NaiveBase implements IQueryProvider {
    @Override
    public Collection<String> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        return map.values().stream()
                .filter(sd -> ((Integer) sd.get(PUNTER_ID_KEY) == punterId))
                .map(sd -> sd.get(ROUTING_KEY_KEY).toString())
                .collect(toList());
    }
}
