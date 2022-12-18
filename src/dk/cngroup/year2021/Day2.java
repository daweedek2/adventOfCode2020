package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.Direction;
import dk.cngroup.Loc;
import dk.cngroup.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static dk.cngroup.year2020.Puzzle16.readString;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day2 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day2 day = new Day2();
        day.solve();
    }

    @Override
    public String getDay() {
        return "2";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return null;
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        Loc p = Utils.dayStream( "\r\n", getDay())
                .map(e -> Utils.readString(e, "%s %n", Move.class))
                .reduce(
                        new Loc(),
                        (acc, m) -> new Loc(m.direction().move(acc.getPoint(), Math.toIntExact(m.n))),
                        Loc::move
                );
        return p.x * p.y;
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<Move> input = Utils.dayStream(System.lineSeparator(), getDay()).map(e -> readString(e, "%s %n", Move.class)).toList();
        Point p = new Point(0, 0);
        long aim = 0;
        for(Move m : input) {
            Direction d = m.direction();
            switch (d) {
                case NORTH -> aim -= m.n;
                case SOUTH -> aim += m.n;
                case EAST -> {
                    p = Direction.EAST.move(p, Math.toIntExact(m.n));
                    p = Direction.SOUTH.move(p, Math.toIntExact(aim * m.n));
                }
                default -> throw new IllegalStateException("There must be a direction.");
            }
        }
        return p.x * p.y;
    }

    public record Move (String dir, long n) {
        public Direction direction(){
            return switch (dir) {
                case "forward" -> Direction.EAST;
                case "up" -> Direction.NORTH;
                case "down" -> Direction.SOUTH;
                default -> throw new IllegalStateException("Unknown dir: " + dir);
            };
        }
    }
}
