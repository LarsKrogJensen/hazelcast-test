package scenarios;

import com.hazelcast.core.IMap;
import common.QueryProvider;
import common.SampleRunner;
import common.SessionData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class NaiveWithBinaryFormat implements QueryProvider {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("binaryInMemory", parseInt(args[0]), new NaiveWithBinaryFormat());
        runner.run();
    }

    @Override
    public List<SessionData> queryRoutingKeysByPunterId(IMap<String, SessionData> map, int punterId) {
        return map.values().stream().filter(data -> ((Integer)data.get(SessionData.PUNTER_ID_KEY) == punterId)).collect(Collectors.toList());
    }
}
