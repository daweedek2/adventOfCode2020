package dk.cngroup;

import java.io.IOException;


public class Puzzle3 {
    private static final String DAY = "3";

    public static void main(String[] args) throws IOException {
        final String[] lines = Utils.getLinesToStringArray(DAY);

        char[][] forest = getArea(lines);

        System.out.println("Number of trees in part one is " + countTrees(forest));
        System.out.println("Number of trees in part two is " + countTreesPartTwo(forest));
    }

    private static int countTreesPartTwo(final char[][] forest) {
        int[] rights = {1,3,5,7,1};
        int[] downs = {1,1,1,1,2};
        int x = 0;
        int y = 0;
        int result = 1;
        char tree = '#';
        int count = 0;

        for (int i = 0; i < 5; i++) {
            x = 0;
            y = 0;
            count = 0;

            while (x+1 < forest.length) {
                x += downs[i];
                y += rights[i];
                if (forest[x][y % forest[x].length] == tree) {
                    count++;
                }
            }
            result *= count;
        }

        return result;
    }

    private static int countTrees(final char[][] forest) {
        int result = 0;
        char tree = '#';
        int sizeY = forest[0].length;
        int down = 1;
        int right = 3;
        int x = 0;
        int y = 0;
        for (int i = 0; i < forest.length - 1; i++) {
            y = y + right;
            x = x + down;
            if (forest[x][y % sizeY] == tree) {
                result++;
            }
        }
        return result;
    }

    private static char[][] getArea(final String[] lines) {
        char[][] forest = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                forest[i][j] = lines[i].charAt(j);
            }
        }
        return forest;
    }
}
