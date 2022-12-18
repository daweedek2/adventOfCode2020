package dk.cngroup;

import java.io.IOException;

/**
 * Created by dkostka on 12/1/2021.
 */
public abstract class AbstractDay {
    public abstract String getDay();
    public abstract Object getPuzzle() throws IOException;
    public abstract Object partOne(Object puzzle) throws IOException;
    public abstract Object partTwo(Object puzzle) throws IOException;

    void printResult(final Object result) {
        System.out.println(result.toString());
    }

    public void solve() throws IOException {
        var day = getDay();
        this.printResult("Part One solution: " + partOne(getPuzzle()));
        this.printResult("Part Two solution: " + partTwo(getPuzzle()));
    }
}
