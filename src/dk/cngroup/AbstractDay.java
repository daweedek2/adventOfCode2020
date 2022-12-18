package dk.cngroup;

import java.io.IOException;

/**
 * Created by dkostka on 12/1/2021.
 */
public abstract class AbstractDay implements Day {
    public abstract String getDay();
    public abstract Object getPuzzle() throws IOException;

    void printResult(final Object result) {
        System.out.println(result.toString());
    }

    public void solve() throws IOException {
        this.printResult("Part One solution: " + partOne(getPuzzle()));
        this.printResult("Part Two solution: " + partTwo(getPuzzle()));
    }
}
