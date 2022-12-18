package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.LongCountMap;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day11 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day11 day = new Day11();
        day.solve();
    }

    @Override
    public String getDay() {
        return "11";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }


    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return solution(20, true);
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        return solution(10000, false);
    }

    public record Monkey(long n, String items, char op, String add, long divisible, long ifTrue, long ifFalse) {}

    private long solution(int cycles, boolean decreasingWorry) throws IOException {
        List<Monkey> monkeys = Arrays.stream(((String) getPuzzle()).split("\r\n\r\n")).map(String::trim).map(s -> readString(s, "Monkey %n:\r\n" +
                "  Starting items: %s\r\n" +
                "  Operation: new = old %c %s\r\n" +
                "  Test: divisible by %n\r\n" +
                "    If true: throw to monkey %n\r\n" +
                "    If false: throw to monkey %n", Monkey.class)).toList();
        Map<Long, List<Long>> items = monkeys.stream().collect(Collectors.toMap(m -> m.n, m -> Arrays.stream(m.items.split(", ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new))));
        LongCountMap<Long> times = new LongCountMap<>();
        long gcd = monkeys.stream().mapToLong(e -> e.divisible).reduce((a,b) -> a*b).getAsLong();
        for(int i = 0; i<cycles; i++) {
            for(Monkey m : monkeys) {
                while(!items.get(m.n).isEmpty()) {
                    long item = items.get(m.n).remove(0);
                    long worryLevel = applyOperator(item, m.op, m.add) / (decreasingWorry ? 3 : 1);
                    boolean test = worryLevel % m.divisible == 0;
                    items.get(test ? m.ifTrue : m.ifFalse).add(worryLevel % gcd);
                    times.increment(m.n);
                }
            }
        }
        long[] sorted = times.values().stream().mapToLong(e -> e).sorted().toArray();
        return sorted[sorted.length-1] * sorted[sorted.length-2];
    }

    private long applyOperator(long item, char op, String add) {
        long itemValue = add.equals("old") ? item : Long.parseLong(add);
        return switch (op) {
            case '*' -> item * itemValue;
            case '+' -> item + itemValue;
            default -> throw new IllegalStateException();
        };
    }
}

