package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.LongCountMap;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day6 extends AbstractDay {
    private List<Day6.Coords> coords;

    public static void main(String args[]) throws IOException {
        Day6 day = new Day6();
        day.solve();
    }

    @Override
    public String getDay() {
        return "6";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        var puzz = (String) puzzle;
        var in = Arrays.stream(puzz.replace("\n", "").split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toCollection(ArrayList::new));
        for(int j = 0; j<80; j++) {
            int size = in.size();
            for (int i = 0; i < size; i++) {
                if (in.get(i) == 0) in.add(8L);
                in.set(i, in.get(i) > 0 ? in.get(i) - 1 : 6);
            }
        }
        return in.size();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        var puzz = (String) puzzle;
        var in = Arrays.stream(puzz.replace("\n", "").split(",")).map(e -> Long.parseLong(e)).collect(Collectors.toCollection(ArrayList::new));
        LongCountMap<Long> cm = new LongCountMap<>();
        for(var i : in) cm.increment(i);
        LongCountMap<Long> nc = new LongCountMap<>();
        for(int j = 0; j<256; j++) {
            for(var e : cm.entrySet()) {
                if(e.getKey() == 0){ nc.increment(8L, e.getValue()); nc.increment(6L, e.getValue()); }else {nc.increment(e.getKey()-1, e.getValue()); }
            }
            cm = nc;
            nc = new LongCountMap<>();
        }
        return cm.values().stream().mapToLong(e -> e).sum();
    }

    public record Coords(long x1, long y1, long x2, long y2) {}
}
