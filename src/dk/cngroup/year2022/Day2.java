package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.stream.Stream;

import static dk.cngroup.year2020.Puzzle16.readString;
import static dk.cngroup.year2022.Day2.Outcome.LOSS;
import static dk.cngroup.year2022.Day2.Outcome.WIN;
import static dk.cngroup.year2022.Day2.Shape.*;


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
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return input().mapToLong(Game::getScore1).sum();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        return input().mapToLong(Game::getScore2).sum();
    }

    private Stream<Game> input () throws IOException {
        return Utils.dayStream("\r\n", "2").map(String::trim).map(s -> readString(s, "%s %s", Game.class));
    }

    enum Shape {ROCK, PAPER, SCISSOR};
    enum Outcome {DRAW, WIN, LOSS};

    public record Game(String a, String b) {
        private Shape getShape(String s){
            return switch (s) {
                case "A", "X" -> ROCK;
                case "B", "Y" -> PAPER;
                case "C", "Z" -> SCISSOR;
                default -> throw new RuntimeException(s);
            };
        }

        private Shape choose(Shape s, Outcome desired) {
            return Shape.values()[(s.ordinal() + desired.ordinal()) % Shape.values().length];
        }

        private long getScore1() {
            return getScore(getShape(b));
        }

        private long getScore(Shape sb) {
            Shape sa = getShape(a);
            long baseScore = sb.ordinal()+1;
            return switch(calculateOutcome(sa, sb)) {
                case LOSS -> baseScore;
                case WIN -> baseScore + 6;
                case DRAW -> baseScore + 3;
            };
        }

        private Outcome calculateOutcome(Shape sa, Shape sb) {
            if(sa == sb) {
                return Outcome.DRAW;
            } else if(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)) {
                return LOSS;
            } else return WIN;
        }

        private long getScore2() {
            return getScore(choose(getShape(a), b.equals("X")? LOSS :b.equals("Y")?Outcome.DRAW: WIN));
        }
    }
}

