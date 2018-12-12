package scenarios;

import common.SampleRunner;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class NaiveWithBinaryFormat extends NaiveBase {
    public static void main(String[] args) throws IOException {
        SampleRunner runner = new SampleRunner("binaryInMemory", parseInt(args[0]), new NaiveWithBinaryFormat());
        runner.run();
    }
}
