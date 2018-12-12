package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class IndexedPredicateWithBinaryFormat extends PredicateBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("binaryInMemoryIndexedWithExtractor", parseInt(args[0]), new IndexedPredicateWithBinaryFormat());
        runner.run();
    }

}
