package dk.cngroup.year2022;

import dk.cngroup.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.Math.toIntExact;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day17 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day17 day = new Day17();
        day.solve();
    }

    @Override
    public String getDay() {
        return "17";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    public record Shape(int w, int h, int b, int t, InfiniteGrid g) {}

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        InfiniteGrid[] shapes = new InfiniteGrid[]{
                new InfiniteGrid(new char[][]{"####".toCharArray()}),
                new InfiniteGrid(new HashMap<>(Map.of(new Loc(1, 0), '#', new Loc(0, 1), '#', new Loc(1, 1), '#', new Loc(2, 1), '#', new Loc(1, 2), '#'))),
                new InfiniteGrid(new HashMap<>(Map.of(new Loc(2, 0), '#', new Loc(0, 2), '#', new Loc(1, 2), '#', new Loc(2, 2), '#', new Loc(2, 1), '#'))),
                new InfiniteGrid(new char[][]{{'#'}, {'#'}, {'#'}, {'#'}}),
                new InfiniteGrid(new char[][]{{'#', '#'}, {'#', '#'}})
        };
        InfiniteGrid g = new InfiniteGrid(new char[][]{"+-------+".toCharArray()});
        addWall(g, 4);
        char[] chars = ((String) getPuzzle()).trim().toCharArray();
        int i = 0;
        int shapeIndex = 0;
        long highest = 0;
        InfiniteGrid s = shapes[shapeIndex].move(3, highest - 4);
        for(int fallenRocks = 0; fallenRocks<2022; i++) {
            if(i>=chars.length) i = 0;
            char c = chars[i];

            if(c == '>') {
                InfiniteGrid moved = s.move(1, 0);
                if(g.canPlace(moved)) {
                    s = moved;
                }
            } else if(c == '<') {
                InfiniteGrid moved = s.move(-1, 0);
                if(g.canPlace(moved)) {
                    s = moved;
                }
            }

            InfiniteGrid moved = s.move(0, 1);
            if(g.canPlace(moved)) {
                s = moved;
            } else {
//        i--;
                g.place(s);
                shapeIndex = (shapeIndex + 1) % shapes.length;
                long oldHeighest = highest;
                highest = Math.min(s.minY(), highest);
                addWall(g, toIntExact((oldHeighest - highest) + shapes[shapeIndex].height()));
                s = shapes[shapeIndex].move(3, highest - 3 - shapes[shapeIndex].height());
                fallenRocks++;
            }

//      InfiniteGrid tog = new InfiniteGrid();
//      tog.place(g);
//      tog.place(s);
//      System.out.println(tog);
        }
        return Math.abs(highest);
    }
    public record State(long height, long fallenRocks, boolean cycleReset) {}


    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        long iterations = 1000000000000L;
        State cycleStart = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks != 0);
        State nextCycle = simulateShapeMoves(iterations, s -> s.cycleReset && s.fallenRocks > cycleStart.fallenRocks);
        long rocks = cycleStart.fallenRocks;
        long height = cycleStart.height;
        while(rocks<iterations){
            rocks += nextCycle.fallenRocks - cycleStart.fallenRocks;
            height += nextCycle.height - cycleStart.height;
        }
        long overshoot = rocks - iterations;
        State atOvershoot = simulateShapeMoves(iterations, s -> s.fallenRocks == cycleStart.fallenRocks - overshoot);
        return height - (cycleStart.height - atOvershoot.height);
    }


    private State simulateShapeMoves(long iterations, Predicate<State> exitCondition) throws IOException {
        InfiniteGrid[] shapes = new InfiniteGrid[]{
                new InfiniteGrid(new char[][]{"####".toCharArray()}),
                new InfiniteGrid(new HashMap<>(Map.of(new Loc(1, 0), '#', new Loc(0, 1), '#', new Loc(1, 1), '#', new Loc(2, 1), '#', new Loc(1, 2), '#'))),
                new InfiniteGrid(new HashMap<>(Map.of(new Loc(2, 0), '#', new Loc(0, 2), '#', new Loc(1, 2), '#', new Loc(2, 2), '#', new Loc(2, 1), '#'))),
                new InfiniteGrid(new char[][]{{'#'}, {'#'}, {'#'}, {'#'}}),
                new InfiniteGrid(new char[][]{{'#', '#'}, {'#', '#'}})
        };
        InfiniteGrid g = new InfiniteGrid(new char[][]{"+-------+".toCharArray()});
        addWall(g, 4);
        char[] chars = ((String) getPuzzle()).trim().toCharArray();
        int i = 0;
        int shapeIndex = 0;
        long highest = 0;
        InfiniteGrid s = shapes[shapeIndex].move(3, highest - 4);
        for(int fallenRocks = 0; fallenRocks<iterations; i++) {
            if(i>=chars.length) i = 0;
            State state = new State(Math.abs(highest), fallenRocks, i==0);
            if(exitCondition.test(state)) {
                return state;
            }

            char c = chars[i];

            if(c == '>') {
                InfiniteGrid moved = s.move(1, 0);
                if(g.canPlace(moved)) {
                    s = moved;
                }
            } else if(c == '<') {
                InfiniteGrid moved = s.move(-1, 0);
                if(g.canPlace(moved)) {
                    s = moved;
                }
            }

            InfiniteGrid moved = s.move(0, 1);
            if(g.canPlace(moved)) {
                s = moved;
            } else {
                g.place(s);
                shapeIndex = (shapeIndex + 1) % shapes.length;
                long oldHeighest = highest;
                highest = Math.min(s.minY(), highest);
                addWall(g, toIntExact((oldHeighest - highest) + shapes[shapeIndex].height()));
                s = shapes[shapeIndex].move(3, highest - 3 - shapes[shapeIndex].height());
                fallenRocks++;
            }
        }
        return new State(Math.abs(highest), iterations, i==0);
    }

    private void addWall(InfiniteGrid g, int n) {
        long lowestY = g.minY()-1;
        for(int i = 0; i<n; i++){
            g.set(new Loc(0, lowestY-i), '|');
            g.set(new Loc(8, lowestY-i), '|');
        }
    }
}

