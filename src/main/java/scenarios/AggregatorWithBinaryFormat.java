package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AggregatorWithBinaryFormat extends AggregatorBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("binaryInMemory", parseInt(args[0]), new AggregatorWithBinaryFormat());
        runner.run();
    }
}
