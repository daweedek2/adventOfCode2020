package dk.cngroup.year2020;

import dk.cngroup.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.IntStream.range;

public class Puzzle15 {
    public static void main(String[] args) throws IOException {
        String day = "15";
        String[] nums = Utils.getLinesToStringArray("15")[0].split(",");
        long[] longs = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            longs[i] = Long.parseLong(nums[i]);
        }

        findSolution(2020L, longs);
        findSolution(30000000, longs);
    }

    private static void findSolution(final long l, final long[] longs) throws IOException {
        Map<Long, Long> turnNumbers = new HashMap<>();

        range(0, longs.length-1)
                .forEach(i -> turnNumbers.put(longs[i], (long)i));
        long lastNumber = longs[longs.length-1];
        for(long turnNumber = turnNumbers.size(); turnNumber <= l - 2L; turnNumber++){
            long newLastNumber = turnNumbers.containsKey(lastNumber) ? turnNumber - turnNumbers.get(lastNumber) : 0;
            turnNumbers.put(lastNumber, turnNumber);
            lastNumber = newLastNumber;
        }

        System.out.println("The solution for " + l + " is " + lastNumber);
    }
}
