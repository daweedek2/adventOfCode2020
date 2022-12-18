package dk.cngroup.year2022;

import dk.cngroup.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static dk.cngroup.Direction.fourDirections;
import static java.util.Arrays.stream;

import static dk.cngroup.InfiniteGrid.toInfiniteGrid;
import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day15 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day15 day = new Day15();
        day.solve();
    }

    @Override
    public String getDay() {
        return "15";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getFileLines(getDay());
    }

    public record Input(long sensorX, long sensorY, long beaconX, long beaconY) {}
    record Pos(Loc sensor, Loc beacon) {}

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        List<Range> posList = input();
        InfiniteGrid g = posList.stream().flatMap(Range::flatten).collect(toInfiniteGrid('X'));
        return IntStream.range(-10000000, 50000000)
                .mapToObj(i -> new Loc(i, 2000000))
                .filter(l -> posList.stream().anyMatch(p -> l.distance(p.start) <= p.distance() && g.get(l).isEmpty()))
                .count();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<Range> posList = input();
        Range target = new Range(new Loc(0, 0), new Loc(4000000, 4000000));
        return input().stream()
                .flatMap(p -> stream(fourDirections()).flatMap(d -> d.move(p.start, p.distance() + 1).walk(d.turnSteps(3), p.distance() + 1)))
                .filter(target::inRange)
                .filter(l -> posList.stream().allMatch(p -> l.distance(p.start) > p.distance()))
                .mapToLong(l -> l.x * 4000000 + l.y)
                .findAny()
                .getAsLong();
    }

    private List<Range> input() throws IOException {
        return ((Stream<String>) getPuzzle()).map(s -> readString(s, "Sensor at x=%n, y=%n: closest beacon is at x=%n, y=%n", Range.class)).toList();
    }
}

