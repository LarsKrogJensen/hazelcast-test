package scenarios;

import com.hazelcast.aggregation.Aggregator;
import com.hazelcast.core.IMap;
import common.QueryProvider;
import common.SampleRunner;
import common.SessionData;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class AggregatorWithObjectFormat implements QueryProvider {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("objectInMemory", parseInt(args[0]), new AggregatorWithObjectFormat());
        runner.run();
    }

    @Override
    public List<SessionData> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        return map.aggregate(new SessionAgg(punterId));
    }

    static class SessionAgg extends Aggregator<Map.Entry<String, SessionData>, List<SessionData>> implements Serializable {

        List<SessionData> sessionData = new ArrayList<>();
        private final int punterId;

        SessionAgg(int punterId) {
            this.punterId = punterId;
        }

        @Override
        public void accumulate(Map.Entry<String, SessionData> input) {
             if ((Integer)input.getValue().get(SessionData.PUNTER_ID_KEY) == punterId) {
                 sessionData.add(input.getValue());
             }
        }

        @Override
        public void combine(Aggregator aggregator) {
            sessionData.addAll(this.getClass().cast(aggregator).sessionData);
        }

        @Override
        public List<SessionData> aggregate() {
            return sessionData;
        }
    }
}
