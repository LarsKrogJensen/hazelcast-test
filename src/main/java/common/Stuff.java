package common;

import java.util.function.Supplier;

import static java.lang.String.format;

public class Stuff {

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

