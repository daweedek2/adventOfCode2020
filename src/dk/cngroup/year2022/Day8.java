package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Direction;
import dk.cngroup.NumGrid;
import dk.cngroup.Utils;

import java.awt.*;
import java.io.IOException;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day8 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day8 day = new Day8();
        day.solve();
    }

    @Override
    public String getDay() {
        return "8";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        NumGrid grid = new NumGrid(((String) getPuzzle()), "\n", "");
        return grid.stream().filter(p -> findEdge(grid, p)).count();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        NumGrid grid = new NumGrid(((String) getPuzzle()), "\n", "");
        return grid.stream().mapToLong(p -> scenicScore(grid, p)).max().getAsLong();
    }

    private boolean findEdge(NumGrid grid, Point p) {
        Direction[] dirs = Direction.fourDirections();
        long num = grid.get(p);
        for(Direction d : dirs) {
            Point newLoc = p;
            while (true) {
                newLoc = d.move(newLoc);
                long atLoc = grid.get(newLoc);
                if(atLoc >= num) break;
                else if(atLoc == -1) return true;
            }
        }
        return false;
    }

    private long scenicScore(NumGrid grid, Point p) {
        Direction[] dirs = Direction.fourDirections();
        long num = grid.get(p);
        long score = 1;
        for(Direction d : dirs) {
            Point newLoc = p;
            long s = 0;
            while (true) {
                newLoc = d.move(newLoc);
                long atLoc = grid.get(newLoc);
                if(atLoc >= num) {s++; break;}
                else if(atLoc == -1) break;
                s++;
            }
            score*=s;
        }
        return score;
    }
}

