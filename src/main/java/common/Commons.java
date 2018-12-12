package common;

import com.hazelcast.core.IMap;

import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class Commons {
    public static final int COUNT_PER_NODE = 100_000;
    public static final int ROUTINGKEYS_PER_NODE = 2;

    public static void populate(IMap<String, SessionData> map, int start, int count, int routingKeysPerPunter) {
        System.out.println("Populating map...");
        durationOf("Populating map took %s ms.",
                   () -> range(start, start + count).forEach(id -> {
                       range(0, routingKeysPerPunter).forEach(multiplier -> {
                           SessionData sessionData = SessionData.create(id, multiplier);
                           map.put(sessionData.getId(), sessionData);
                       });
                   })
        );
    }

    public static void durationOf(String format, Runnable block) {
        long start = System.currentTimeMillis();
        block.run();
        long stop = System.currentTimeMillis();
        System.out.println(format(format, (stop - start)));
    }

    public static <T> T durationOf(String format, Supplier<T> block) {
        long start = System.currentTimeMillis();
        T result = block.get();
        long stop = System.currentTimeMillis();
        System.out.println(format(format, (stop - start)));
        return result;
    }
}

