package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class IndexedPredicateWithObjectFormat extends PredicateBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("objectInMemoryIndexedWithExtractor", parseInt(args[0]), new IndexedPredicateWithObjectFormat());
        runner.run();
    }
}
