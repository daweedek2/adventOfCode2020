package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static dk.cngroup.Utils.readString;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day5 extends AbstractDay {
    private List<Day5.Coords> coords;

    public static void main(String args[]) throws IOException {
        Day5 day = new Day5();
        day.solve();
    }

    @Override
    public String getDay() {
        return "5";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.dayStream("\r\n", getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        var puzzleStream = (Stream<String>) puzzle;
        coords = puzzleStream.map(e -> readString(e, "%n,%n -> %n,%n", Coords.class)).toList();
        Set<Point> all = new HashSet<>();
        Set<Point> vis = new HashSet<>();
        for(Coords c : coords) {
            if(c.x1 == c.x2){
                for(long y = Math.min(c.y1, c.y2); y<=Math.max(c.y1, c.y2); y++){
                    var l = new Point(Math.toIntExact(c.x1), Math.toIntExact(y));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if(c.y1 == c.y2){
                for(long x = Math.min(c.x1, c.x2); x<=Math.max(c.x1, c.x2); x++){
                    var l = new Point(Math.toIntExact(x), Math.toIntExact(c.y1));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }
        }
        return vis.size();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {

        Set<Point> all = new HashSet<>();
        Set<Point> vis = new HashSet<>();
        for(Coords c : coords) {
            if(c.x1 == c.x2){
                for(long y = Math.min(c.y1, c.y2); y<=Math.max(c.y1, c.y2); y++){
                    var l = new Point(Math.toIntExact(c.x1), Math.toIntExact(y));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if(c.y1 == c.y2){
                for(long x = Math.min(c.x1, c.x2); x<=Math.max(c.x1, c.x2); x++){
                    var l = new Point(Math.toIntExact(x), Math.toIntExact(c.y1));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else if((c.x1 > c.x2 && c.y1 > c.y2) || (c.x1 < c.x2 && c.y1 < c.y2)){
                for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
                    var l = new Point(Math.toIntExact(Math.min(c.x1, c.x2)+x), Math.toIntExact(Math.min(c.y1, c.y2)+x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }else if(c.x1 < c.x2 && c.y1 > c.y2){
                for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
                    var l = new Point(Math.toIntExact(c.x1+x), Math.toIntExact(c.y1-x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            }else if(c.x1 > c.x2 && c.y1 < c.y2){
                for(long x = 0; x<=Math.max(c.x1, c.x2)-Math.min(c.x1, c.x2); x++){
                    var l = new Point(Math.toIntExact(c.x1-x), Math.toIntExact(c.y1+x));
                    if(!all.add(l)){
                        vis.add(l);
                    }
                }
            } else throw new IllegalStateException("not covered "+c);
        }
        return vis.size();

    }

    public record Coords(long x1, long y1, long x2, long y2) {}
}
