package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.stream.LongStream;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day7 extends AbstractDay {
    private LongStream puzz;

    public static void main(String args[]) throws IOException {
        Day7 day = new Day7();
        day.solve();
    }

    @Override
    public String getDay() {
        return "7";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.longStream(",", getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        puzz = (LongStream) puzzle;
        return puzz.map(p -> {
            try {
                return sol(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return p;
        }).min().getAsLong();
    }

    private long sol(final long l) throws IOException {
        return getSteps(l).sum();
    }

    private LongStream getSteps(final long guess) throws IOException {
        var p2 = (LongStream) getPuzzle();
        return p2.map(n -> guess > n ? guess - n : n - guess);
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        var p1 = (LongStream) getPuzzle();
        return p1.map(p -> {
            try {
                return sol2(p);
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }).min().getAsLong();
    }

    private long sol2(final long l) throws IOException {
        return getSteps(l).map(n -> n*(n+1)/2).sum();
    }
}
