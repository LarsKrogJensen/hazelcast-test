package common;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;
import static common.Commons.*;
import static common.SessionData.composeSessionId;
import static java.lang.Integer.parseInt;

public class SampleRunner implements Runnable {

    private final HazelcastInstance hazelcast;
    private final IMap<String, SessionData> map;
    private final int node;
    private final QueryProvider provider;

    public SampleRunner(String mapName, int node, QueryProvider provider) {
        this.provider = provider;
        this.hazelcast = newHazelcastInstance();
        this.map = hazelcast.getMap(mapName);
        this.node = node;
        System.out.println("InMemoryFormat: " + hazelcast.getConfig().findMapConfig(mapName).getInMemoryFormat());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter command:");
            String next = scanner.nextLine();
            String[] command = next.split(" ");

            if (command.length > 0) {
                if (command[0].equalsIgnoreCase("rk") && command.length == 2) {
                    queryRoutingKey(command[1]);
                } else if (command[0].equalsIgnoreCase("pk") && command.length == 3) {
                    queryPrimaryKey(command[1], command[2]);
                } else if (command[0].equalsIgnoreCase("quit")) {
                    hazelcast.shutdown();
                    break;
                } else if (command[0].equalsIgnoreCase("size")) {
                    System.out.println("map size: " + map.size() + ", localSize: " + map.localKeySet().size());
                } else if (command[0].equalsIgnoreCase("populate")) {
                    populate(map, node * COUNT_PER_NODE, COUNT_PER_NODE, ROUTINGKEYS_PER_NODE);
                } else {
                    System.err.println("Bad command");
                }

            }
        }
    }

    private void queryPrimaryKey(String arg1, String arg2) {
        SessionData data = durationOf("Query by primary key took %s ms", () -> map.get(composeSessionId(parseInt(arg1), parseInt(arg2))));
        System.out.println("Got result back: " + (data != null ? data.getId() : "<null>"));
    }

    private void queryRoutingKey(String arg) {
        Collection<SessionData> data = durationOf("Query by primary key took %s ms", () -> provider.queryRoutingKeysByPunterId(map, parseInt(arg)));
        System.out.println("Got result back with sessionIds:" + data.stream().map(SessionData::getId).collect(Collectors.joining(", ")));
    }
}
