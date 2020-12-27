package dk.cngroup;

import java.io.IOException;

public class Puzzle1 {

    private static final String DAY = "1";

    public static void main(String[] args) throws IOException {

        Integer[] puzzle = Utils.getLinesToIntegerArray(DAY);
        partOne(puzzle);
        partTwo(puzzle);

    }

    private static void partOne(final Integer[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = i+1; j < puzzle.length; j++) {
                if (puzzle[i] + puzzle[j] == 2020) {
                    System.out.println("result of part one is " + puzzle[i] * puzzle[j]);
                }
            }
        }
    }

    private static void partTwo(final Integer[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = i+1; j < puzzle.length; j++) {
                for (int k = j+1; k < puzzle.length; k++) {
                    if (puzzle[i] + puzzle[j] + puzzle[k] == 2020) {
                        System.out.println("result of part two is " + puzzle[i] * puzzle[j] * puzzle[k]);
                    }
                }
            }
        }
    }
}

