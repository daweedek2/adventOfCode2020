package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dkostka on 12/3/2022.
 */
public class Day3 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day3 day = new Day3();
        day.solve();
    }

    @Override
    public String getDay() {
        return "3";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.dayStream(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return ((Stream<String>) getPuzzle())
                .map(String::trim)
                .map(e -> new String[]{e.substring(0, e.length()/2), e.substring(e.length()/2)})
                .mapToInt(e -> getPriorities(e[0]).filter(i -> getPriorities(e[1]).anyMatch(j -> j == i)).findFirst().getAsInt())
                .sum();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        String[] s = ((Stream<String>) getPuzzle()).map(String::trim).toArray(String[]::new);
        return IntStream.range(0, s.length/3)
                .map(x -> x * 3)
                .map(x -> getPriorities(s[x]).filter(i -> getPriorities(s[x+1]).anyMatch(j -> j == i) && getPriorities(s[x+2]).anyMatch(j -> j == i)).findFirst().getAsInt())
                .sum();
    }

    private IntStream getPriorities(String s) {
        return s.chars().map(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26);
    }
}

