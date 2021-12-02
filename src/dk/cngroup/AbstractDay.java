package dk.cngroup;

import java.io.IOException;

/**
 * Created by dkostka on 12/1/2021.
 */
public abstract class AbstractDay {
    public abstract String getDay();
    public abstract Object getPuzzle(String day) throws IOException;
    public abstract Object partOne(Object puzzle) throws IOException;
    public abstract Object partTwo(Object puzzle) throws IOException;

    void printResult(final Object partOne, final Object partTwo) {
        System.out.println("Part One solution: " + partOne.toString());
        System.out.println("Part Two solution: " + partTwo.toString());
    }

    public void solve() throws IOException {
        var day = getDay();
        var puzzle = getPuzzle(day);
        this.printResult(partOne(puzzle), partTwo(puzzle));
    }
}
