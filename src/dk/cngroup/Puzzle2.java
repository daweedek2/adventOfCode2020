package dk.cngroup;

import java.io.IOException;
import java.util.Arrays;

public class Puzzle2 {
    private static final String DAY = "2";

    public static void main(String[] args) throws IOException {
        final String[] passwords = Utils.getLinesToStringArray(DAY);

        long partOneResult = Arrays.stream(passwords)
                .filter(Puzzle2::validatePasswordsPartOne)
                .count();

        long partTwoResult = Arrays.stream(passwords)
                .filter(Puzzle2::validatePasswordsPartTwo)
                .count();

        System.out.println("The result of part one is " + partOneResult);
        System.out.println("The result of part two is " + partTwoResult);
    }



    private static boolean validatePasswordsPartTwo(final String password1) {
        String[] parsedLine = password1.split("\\s+");
        String[] limits = parsedLine[0].split("-");
        int firstIndex = Integer.parseInt(limits[0].trim()) - 1;
        int secondIndex = Integer.parseInt(limits[1].trim()) - 1;
        char letter = parsedLine[1].charAt(0);
        String password = parsedLine[2];

        return password.charAt(firstIndex) == letter && password.charAt(secondIndex) != letter
        || password.charAt(firstIndex) != letter && password.charAt(secondIndex) == letter;
    }


    private static boolean validatePasswordsPartOne(final String password1) {
        String[] parsedLine = password1.split("\\s+");
        String[] limits = parsedLine[0].split("-");
        int lowLimit = Integer.parseInt(limits[0].trim());
        int highLimit = Integer.parseInt(limits[1].trim());
        char letter = parsedLine[1].charAt(0);
        String password = parsedLine[2];
        long count = password
                .chars()
                .filter(ch -> ch == letter)
                .count();

        if (count >= lowLimit && count <= highLimit) {
            return true;
        }

        return false;
    }
}
