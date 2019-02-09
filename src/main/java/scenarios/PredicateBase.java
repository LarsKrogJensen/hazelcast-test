package scenarios;

import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import common.IQueryProvider;
import common.SessionData;

import java.util.Collection;

import static common.SessionData.ROUTING_KEY_KEY;
import static java.util.stream.Collectors.toList;

public class PredicateBase implements IQueryProvider {
    @Override
    public Collection<String> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("punterId").equal(punterId);

        return map.values(predicate).stream()
                .map(sd -> sd.get(ROUTING_KEY_KEY).toString())
                .collect(toList());
    }
}
