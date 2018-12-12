package scenarios;

import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import common.QueryProvider;
import common.SampleRunner;
import common.SessionData;

import java.io.IOException;
import java.util.Collection;

import static java.lang.Integer.parseInt;

public class PredicateWithObjectFormat implements QueryProvider {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("objectInMemoryWithExtractor", parseInt(args[0]), new PredicateWithObjectFormat());
        runner.run();
    }

    @Override
    public Collection<SessionData> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("punterId").equal(punterId);
        return map.values(predicate);
    }

}
