package dk.cngroup;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Puzzle6 {
    private static final String DAY = "6";

    public static void main(String[] args) throws IOException {
        String content = Utils.getString(DAY);

        String[] groups = content.split("\n\\s*\n");

        int resultPartOne = Arrays.stream(groups)
                .mapToInt(Puzzle6::getPartCountPartOne)
                .sum();

        int resultPartTwo = Arrays.stream(groups)
                .mapToInt(Puzzle6::getCountPartTwo)
                .sum();

        System.out.println(resultPartOne);
        System.out.println(resultPartTwo);
    }

    private static int getCountPartTwo(final String group) {
        int numberOfPersons  = group.split("[\\r]").length;

        String allChars = group.replaceAll("[^a-z]", "");
        long count = allChars.chars()
                .distinct()
                .filter(ch -> isValid(numberOfPersons, (char) ch, allChars))
                .count();

        return (int) count;
    }

    private static boolean isValid(final int numberOfPersons, final char ch, final String allChars) {
        return allChars.codePoints().filter(c -> c == ch).count() == numberOfPersons;
    }

    private static int getPartCountPartOne(final String group) {
        String reduced = group.replaceAll("[\\n\\r ]", "");
        IntStream uniqueYeses = reduced.chars().distinct();

        return (int) uniqueYeses.count();
    }
}
