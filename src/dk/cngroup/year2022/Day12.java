package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.LongCountMap;
import dk.cngroup.NumGrid;
import dk.cngroup.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day12 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day12 day = new Day12();
        day.solve();
    }

    @Override
    public String getDay() {
        return "12";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }


    @Override
    public Object partOne(final Object puzzle) throws IOException {
        NumGrid g = input();
        return findExit(g.find('S'), g);
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        NumGrid g = input();
        return g.findAll('a').filter(p -> p.y == 0).mapToLong(p -> findExit(p, g)).min().getAsLong();
    }

    public NumGrid input() throws IOException {
        return new NumGrid(Arrays.stream(Utils.dayGrid(getDay())).map(e -> new String(e).chars().mapToLong(f -> f).toArray()).toArray(long[][]::new));
    }

    private long findExit(Point p9, NumGrid g) {
        Set<Point> visited = new HashSet<>();
        Set<Point> currentLevel = new HashSet<>();
        currentLevel.add(p9);
        visited.add(p9);

        long steps = 0;
        while(!currentLevel.isEmpty()){
            Set<Point> level = new HashSet<>(currentLevel);
            currentLevel.clear();
            for(Point p : level) {
                long current = g.get(p);
                if(current == 'S') current = 'a';
                for(Point p2 : g.streamDirs(p).toList()) {
                    if((current == 'y' || current == 'z') && g.get(p2) == 'E') return steps+1;
                    if(g.get(p2) <= current+1 && visited.add(p2)) {
                        currentLevel.add(p2);
                    }
                }
            }
            steps++;
        }
        return Long.MAX_VALUE;
    }
}

