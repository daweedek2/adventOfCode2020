package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day10 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day10 day = new Day10();
        day.solve();
    }

    @Override
    public String getDay() {
        return "10";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getLinesToList(getDay());
    }


    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return performOp(this::signalStrength).mapToLong(e -> e).sum();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        return performOp(this::getPixel).collect(Collectors.joining()); // answer is REHPRLUB
    }

    private long signalStrength(long cycle, long x) {
        return (cycle+20) % 40 == 0 ? cycle*x : 0;
    }

    private String getPixel(long cycle, long x) {
        long i = (cycle -1) % 40;
        return (i == 0 ? "\n" : "") + (List.of(x -1, x, x +1).contains(i) ? "#" : ".");
    }

    private<T> Stream<T> performOp(BiFunction<Long, Long, T> func) throws IOException {
        long cycle = 1;
        long x  = 1;
        List<T> res = new ArrayList<>();
        for(String op : ((List<String>) getPuzzle())) {
            res.add(func.apply(cycle, x));
            if(op.startsWith("addx")) {
                cycle++;
                res.add(func.apply(cycle, x));
                x+=Long.parseLong(op.substring(5));
            }
            cycle++;
        }
        return res.stream();
    }
}

