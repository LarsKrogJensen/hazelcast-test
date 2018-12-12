package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PredicateWithBinaryFormat extends PredicateBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("binaryInMemoryWithExtractor", parseInt(args[0]), new PredicateWithBinaryFormat());
        runner.run();
    }
}
