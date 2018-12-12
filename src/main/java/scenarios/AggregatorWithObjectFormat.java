package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AggregatorWithObjectFormat extends AggregatorBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("objectInMemory", parseInt(args[0]), new AggregatorWithObjectFormat());
        runner.run();
    }

}
