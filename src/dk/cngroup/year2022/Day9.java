package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Direction;
import dk.cngroup.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day9 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day9 day = new Day9();
        day.solve();
    }

    @Override
    public String getDay() {
        return "9";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.dayStream(getDay());
    }

    public record Move(String dir, long n){}

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        List<Move> moves = ((Stream<String>) getPuzzle()).map(s -> readString(s, "%s %n", Move.class)).toList();
        Point p1 = new Point();
        Point p2 = new Point();
        HashSet<Point> vis = new HashSet<>();
        vis.add(p2);
        for(Move m : moves) {
            Direction dir = Direction.getByDirCode(m.dir.charAt(0));
            for(int i = 0; i<m.n; i++) {
                p1 = dir.move(p1);
                Point p12 = p1, p22 = p2;
                if(Arrays.stream(Direction.values()).noneMatch(d -> d.move(p22).equals(p12))) {
                    if(p1.x > p2.x && p1.y == p2.y) {
                        p2 = Direction.EAST.move(p2);
                    } else if(p1.x < p2.x && p1.y == p2.y){
                        p2 = Direction.WEST.move(p2);
                    } else if(p1.x == p2.x && p1.y > p2.y) {
                        p2 = Direction.SOUTH.move(p2);
                    } else if(p1.x == p2.x && p1.y < p2.y){
                        p2 = Direction.NORTH.move(p2);
                    } else if(p1.x > p2.x && p1.y > p2.y) {
                        p2 = Direction.SOUTHEAST.move(p2);
                    } else if(p1.x < p2.x && p1.y < p2.y){
                        p2 = Direction.NORTHWEST.move(p2);
                    } else if(p1.x < p2.x && p1.y > p2.y) {
                        p2 = Direction.SOUTHWEST.move(p2);
                    } else if(p1.x > p2.x && p1.y < p2.y){
                        p2 = Direction.NORTHEAST.move(p2);
                    } else throw new IllegalStateException();
                }
                vis.add(p2);
            }
        }
        return vis.size();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<Move> moves = ((Stream<String>) getPuzzle()).map(s -> readString(s, "%s %n", Move.class)).toList();
        Point p1 = new Point();
        List<Point> p2 = new java.util.ArrayList<>(IntStream.range(0, 9).mapToObj(i -> new Point()).toList());
        HashSet<Point> vis = new HashSet<>();
        vis.add(p1);

        for(Move m : moves) {
            Direction dir = Direction.getByDirCode(m.dir.charAt(0));
            for(int i = 0; i<m.n; i++) {
//        NumGrid g = new NumGrid(new long[6][6]);
                p1 = dir.move(p1);
//        g.set(new Point(Math.abs(p1.x), Math.abs(p1.y)), 11);
                for(int j = 0; j<p2.size(); j++) {
                    Point p12 = j == 0 ? p1 : p2.get(j - 1), p22 = p2.get(j);
                    Point p23 = p22;
                    if (Arrays.stream(Direction.values()).noneMatch(d -> d.move(p23).equals(p12))) {
                        if (p12.x > p22.x && p12.y == p22.y) {
                            p22 = Direction.EAST.move(p22);
                        } else if (p12.x < p22.x && p12.y == p22.y) {
                            p22 = Direction.WEST.move(p22);
                        } else if (p12.x == p22.x && p12.y > p22.y) {
                            p22 = Direction.SOUTH.move(p22);
                        } else if (p12.x == p22.x && p12.y < p22.y) {
                            p22 = Direction.NORTH.move(p22);
                        } else if (p12.x > p22.x && p12.y > p22.y) {
                            p22 = Direction.SOUTHEAST.move(p22);
                        } else if (p12.x < p22.x && p12.y < p22.y) {
                            p22 = Direction.NORTHWEST.move(p22);
                        } else if (p12.x < p22.x && p12.y > p22.y) {
                            p22 = Direction.SOUTHWEST.move(p22);
                        } else if (p12.x > p22.x && p12.y < p22.y) {
                            p22 = Direction.NORTHEAST.move(p22);
                        } else throw new IllegalStateException();
                    }
                    p2.set(j, p22);
                    if(j == p2.size()-1) vis.add(p22);
//          g.set(new Point(Math.abs(p22.x), Math.abs(p22.y)), j+1);
                }
//        System.out.println(g);
            }
        }
        return vis.size();
    }
}

