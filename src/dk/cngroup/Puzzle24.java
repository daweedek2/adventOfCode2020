package dk.cngroup;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Puzzle24 {
    public static void main(String[] args) throws IOException {
        Puzzle24 puzzle24 = new Puzzle24();
        System.out.println(puzzle24.part1());
        System.out.println(puzzle24.part2());
    }

    Set<Point> visited = new HashSet<>();

    public Object part1() throws IOException {
        var input = Arrays.stream(Utils.getLinesToStringArray("24")).map(this::read).collect(toList());
        for(List<HexDirection> dirs : input){
            Point pos = new Point(0,0);
            for(HexDirection dir : dirs){
                pos = dir.move(pos);
            }
            if(visited.contains(pos)){
                visited.remove(pos);
            } else {
                visited.add(pos);
            }
        }
        return visited.size();
    }

    public List<HexDirection> read(String dirs){
        List<HexDirection> res = new ArrayList<>(dirs.length());
        while(dirs.length()>0){
            Optional<HexDirection> direction;
            if(dirs.length()>1 && (direction = HexDirection.get(dirs.substring(0,2))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(2);
            } else if ((direction = HexDirection.get(dirs.substring(0,1))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(1);
            }else {
                throw new IllegalStateException("Panik!");
            }

        }
        return res;
    }

    public Object part2() {
        for(int i = 0; i<100; i++){
            Set<Point> newPos = new HashSet<>();
            Set<Point> checkedPos = new HashSet<>();
            for(Point p : visited) {
                addNeighbors(visited, newPos, checkedPos, p, true);
            }
            visited = newPos;
        }
        return visited.size();
    }

    public void addNeighbors(Set<Point> pos, Set<Point> newPos, Set<Point> checkedPos, Point p, boolean active){
        if(!checkedPos.contains(p)) {
            int neighbours = 0;
            checkedPos.add(p);
            for(HexDirection dir : HexDirection.values()) {
                Point x = dir.move(p);
                if (pos.contains(x)) {
                    neighbours++;
                } else if (active) {
                    addNeighbors(pos, newPos, checkedPos, x, false);
                }
            }
            if((active && (neighbours == 1 || neighbours == 2)) ||
                    (!active && neighbours == 2)){
                newPos.add(p);
            }
        }
    }

    public enum HexDirection {
        NORTHWEST(1, "nw"), EAST(4, "e"), SOUTHEAST(2, "se"), WEST(3, "w"),
        NORTHEAST(4, "ne"), SOUTHWEST(5, "sw");

        public final int num;
        public final String code;

        HexDirection(int num, String code) {
            this.num = num;
            this.code = code;
        }

        public static Optional<HexDirection> get(String code) {
            return Arrays.stream(values()).filter(e -> e.code.equals(code)).findAny();
        }

        public Point move(Point currentLocation, int amount) {
            switch (this) {
                case EAST: return new Point(currentLocation.x+(amount*2), currentLocation.y);
                case WEST: return new Point(currentLocation.x-(amount*2), currentLocation.y);
                case SOUTHWEST: return new Point(currentLocation.x-amount, currentLocation.y+amount);
                case NORTHEAST: return new Point(currentLocation.x+amount, currentLocation.y-amount);
                case SOUTHEAST: return new Point(currentLocation.x+amount, currentLocation.y+amount);
                case NORTHWEST: return new Point(currentLocation.x-amount, currentLocation.y-amount);
            }
            throw new IllegalStateException("Non-existent Direction: "+this);
        }

        public Point move(Point currentLocation) {
            return move(currentLocation, 1);
        }

        public HexDirection opposite() {
            switch (this) {
                case EAST: return WEST;
                case WEST: return EAST;
                case NORTHEAST: return SOUTHWEST;
                case SOUTHWEST: return NORTHEAST;
                case SOUTHEAST: return NORTHWEST;
                case NORTHWEST: return SOUTHEAST;
            }
            throw new IllegalStateException("Non-existent Direction: "+this);
        }
    }
}
