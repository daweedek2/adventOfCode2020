package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day19 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day19 day = new Day19();
        day.solve();
    }

    @Override
    public String getDay() {
        return "19";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getFileLines(getDay());
    }

    @Override
    public Object partOne(Object puzzle) throws IOException {
        return super.partOne(puzzle);
    }

    @Override
    public Object partTwo(Object puzzle) throws IOException {
        return super.partTwo(puzzle);
    }
}

