package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PredicateWithObjectFormat extends PredicateBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("objectInMemoryWithExtractor", parseInt(args[0]), new PredicateWithObjectFormat());
        runner.run();
    }

}
