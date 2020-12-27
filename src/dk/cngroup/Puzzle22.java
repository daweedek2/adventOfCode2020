package dk.cngroup;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toCollection;

public class Puzzle22 {
    public static void main(String[] args) throws IOException {
        System.out.println("part1: " + part1());
        System.out.println("pat2: " + part2());
    }

    public static Object part1() throws IOException {
        String[] input = Utils.getStringWithSingleLineSeparator("22").split("\n\n");
        Deque<Long> p1 = getInput(0, input);
        Deque<Long> p2 = getInput(1, input);

        while(p1.size()>0 && p2.size() > 0){
            long l1 = p1.pop();
            long l2 = p2.pop();
            if(l1 > l2){
                p1.add(l1);
                p1.add(l2);
            } else {
                p2.add(l2);
                p2.add(l1);
            }
        }
        Deque<Long> winner = p1.size()>0 ? p1 : p2;
        return calcScore(winner);
    }

    private static long calcScore(Deque<Long> winner) {
        return LongStream.rangeClosed(1, winner.size()).boxed().sorted(Comparator.reverseOrder()).mapToLong(l -> winner.pop() * l).sum();
    }

    public static Object part2() throws IOException {
        String[] input = Utils.getStringWithSingleLineSeparator("22").split("\n\n");
        Deque<Long> p1 = getInput(0, input);
        Deque<Long> p2 = getInput(1, input);
        return calcScore(playGame(p1, p2) == Player.P1 ? p1 : p2);
    }

    private static ArrayDeque<Long> getInput(int i, String[] input) {
        return Arrays.stream(input[i].split("\n")).filter(e -> !e.startsWith("Player")).map(Long::parseLong).collect(toCollection(ArrayDeque::new));
    }

    public static Player playGame(Deque<Long> p1, Deque<Long> p2){
        Set<List<Long>> playedGames = new HashSet<>();
        while(p1.size()>0 && p2.size() > 0){
            if(!playedGames.add(new ArrayList<>(p1))){
                return Player.P1;
            }

            long l1 = p1.pop();
            long l2 = p2.pop();
            if(p1.size() < l1 || p2.size() < l2){
                if(l1 > l2){
                    p1.add(l1);
                    p1.add(l2);
                } else {
                    p2.add(l2);
                    p2.add(l1);
                }
            } else {
                if(playGame(new ArrayDeque<>(new ArrayList<>(p1).subList(0, toIntExact(l1))), new ArrayDeque<>(new ArrayList<>(p2).subList(0, toIntExact(l2)))) == Player.P1){
                    p1.add(l1);
                    p1.add(l2);
                } else {
                    p2.add(l2);
                    p2.add(l1);
                }
            }
        }
        return p1.size()>0 ? Player.P1 : Player.P2;
    }

    enum Player{P1, P2}
}
