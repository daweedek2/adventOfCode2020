package dk.cngroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzle9 {
    private static int preambule = 25;
    private static int partOneResult = 26134589;
    public static void main(String[] args) throws IOException {
        String day = "9";
        var ints = Utils.getLinesToLongArray(day);
        partOne(ints);
        partTwo(ints);
    }

    private static void partTwo(final Long[] ints) {

        for (int i = 0; i < ints.length; i++) {
            long sum = ints[i];
            for (int j = i + 1; j < ints.length; j++) {
                sum += ints[j];
                if (sum == partOneResult) {
                    getPartTwoResult(i, j, ints);
                }
            }
        }
    }

    private static void getPartTwoResult(final int startIndex, final int stopIndex, final Long[] ints) {
        List<Long> list = new ArrayList<>();
        for (int i = startIndex; i <= stopIndex; i++) {
            list.add(ints[i]);
        }
        long max = list.stream()
                .mapToLong(Long::longValue)
                .max().orElseThrow();

        long min = list.stream()
                .mapToLong(Long::longValue)
                .min().orElseThrow();

        long result = max + min;

        System.out.println("Part two solution: " + max + " + " + min + " = " + result);
    }

    private static void partOne(final Long[] ints) {
        int minIndex = 0;

        for (int i = preambule; i < ints.length; i++) {
            minIndex = i - preambule;
            Long[] recent25 = get25AtIndex(ints, minIndex);
            List<Long> validNumbers = calculate(recent25);
            if (isNotValid(ints[i], validNumbers)) {
                System.out.println("Part one result: " + ints[i]);
            }
        }
    }

    private static boolean isNotValid(final Long anInt, final List<Long> validNumbers) {
        return !validNumbers.contains(Long.valueOf(anInt));
    }

    private static List<Long> calculate(final Long[] recent25) {
        List<Long> validOnes = new ArrayList<>();
        for (int i = 0; i < recent25.length; i++) {
            for (int j = i+1; j < recent25.length; j++) {
                validOnes.add(recent25[i] + recent25[j]);
            }
        }
        return validOnes;
    }

    private static Long[] get25AtIndex(final Long[] ints, final int minIndex) {
        Long[] recent25 = new Long[preambule];
        for (int i = 0; i < preambule; i++) {
            recent25[i] = ints[i + minIndex];
        }
        return recent25;
    }
}
