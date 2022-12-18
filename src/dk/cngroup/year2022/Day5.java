package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day5 extends AbstractDay {

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
        return (Utils.dayStream(getDay()));
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        List<Deque<Integer>> stacks = input();
        List<Move> moves = getMoves();
        for(Move m : moves) {
            for(int i = 0; i<m.which; i++) {
                int top = stacks.get(Math.toIntExact(m.from-1)).removeLast();
                stacks.get(Math.toIntExact(m.to-1)).addLast(top);
            }
        }
        return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<Deque<Integer>> stacks = input();
        List<Move> moves = getMoves();
        for(Move m : moves) {
            List<Integer> toBeMoved = new ArrayList<>();
            for(int i = 0; i<m.which; i++) toBeMoved.add(0, stacks.get(Math.toIntExact(m.from-1)).removeLast());
            toBeMoved.forEach(i -> stacks.get(Math.toIntExact(m.to-1)).addLast(i));
        }
        return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
    }

    public record Move(long which, long from, long to) {}

    private List<Move> getMoves() throws IOException {
        return ((Stream<String>) getPuzzle()).map(String::trim).map(s -> readString(s, "move %n from %n to %n", Move.class)).toList();
    }

//    private static List<Deque<Integer>> input() {
//        List<Deque<Integer>> stacks = new ArrayList<>();
//        for(int i = 1; i<=9; i++){
//            Deque<Integer> s = new ArrayDeque<>();
//            switch (i) {
//                case 1 -> "QFLSR".chars().forEach(s::add);
//                case 2 -> "TPGQZN".chars().forEach(s::add);
//                case 3 -> "BQMS".chars().forEach(s::add);
//                case 4 -> "QBCHJZGT".chars().forEach(s::add);
//                case 5 -> "SFNBMHP".chars().forEach(s::add);
//                case 6 -> "GVLSNQCP".chars().forEach(s::add);
//                case 7 -> "FCW".chars().forEach(s::add);
//                case 8 -> "MPVWZGHQ".chars().forEach(s::add);
//                case 9 -> "RNCLDZG".chars().forEach(s::add);
//            }
//            stacks.add(s);
//        }
//        return stacks;
//    }

    private static List<Deque<Integer>> input() {
        List<Deque<Integer>> stacks = new ArrayList<>();
        for(int i = 1; i<=9; i++){
            Deque<Integer> s = new ArrayDeque<>();
            switch (i) {
                case 1 -> "RSLFQ".chars().forEach(s::add);
                case 2 -> "NZQGPT".chars().forEach(s::add);
                case 3 -> "SMQB".chars().forEach(s::add);
                case 4 -> "TGZJHCBQ".chars().forEach(s::add);
                case 5 -> "PHMBNFS".chars().forEach(s::add);
                case 6 -> "PCQNSLVG".chars().forEach(s::add);
                case 7 -> "WCF".chars().forEach(s::add);
                case 8 -> "QHGZWVPM".chars().forEach(s::add);
                case 9 -> "GZDLCNR".chars().forEach(s::add);
            }
            stacks.add(s);
        }
        return stacks;
    }

}

