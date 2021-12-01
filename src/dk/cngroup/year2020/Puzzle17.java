package dk.cngroup.year2020;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle17 {

    public static void main(String[] args) {
        ArrayList<String> input;
        Scanner reader = null;
        try {
            reader = new Scanner(new File("/Java/adventOfCode2020/puzzle17.txt"));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        input = new ArrayList<String>();

        while (reader.hasNext()) {
            input.add(reader.nextLine());
        }

        part1(input);
        part2(input);
    }

    public static void part1(final ArrayList<String> input) {
        char[][][] grid;
        // create 3D char array that's hopefully bigger than we need
        grid = new char[input.size() + 20][input.size() + 20][input.size() + 20];
        // initialize all cubes to inactive (.)
        for (int z = 0; z < grid.length; z++) {
            for (int y = 0; y < grid[0].length; y++) {
                for (int x = 0; x < grid[0][0].length; x++) {
                    grid[z][y][x] = '.';
                }
            }
        }

        // initialize middle z-layer to input state
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(0).length(); x++) {
                char c = input.get(y).charAt(x);
                // grid[middle z][middle y - half of input length][middle x - half input length]
                grid[grid.length / 2 - input.size() / 2][grid.length / 2 - input.size() / 2 + y][grid.length / 2
                        + x] = c;
            }
        }

        for (int cycle = 1; cycle <= 6; cycle++) {
            char[][][] copy = new char[grid.length][grid.length][grid.length];
            for (int z = 0; z < grid.length; z++) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid.length; x++) {
                        int activeNeighbors = countNeighbors1(grid, z, y, x);
                        if (grid[z][y][x] == '#') {
                            if (activeNeighbors == 2 || activeNeighbors == 3) {
                                copy[z][y][x] = '#';
                            } else {
                                copy[z][y][x] = '.';
                            }
                        } else if (grid[z][y][x] == '.') {
                            if (activeNeighbors == 3) {
                                copy[z][y][x] = '#';
                            } else {
                                copy[z][y][x] = '.';
                            }
                        }
                    }
                }
            }
            grid = copy;
        }

        int active = 0;
        for (int z = 0; z < grid.length; z++) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid.length; x++) {
                    if (grid[z][y][x] == '#') {
                        active++;
                    }
                }
            }
        }

        System.out.println("Active cubes: " + active);

    }

    public static void part2(final ArrayList<String> input) {
        char[][][][] grid;
        // create 3D char array that's hopefully bigger than we need
        grid = new char[input.size() + 20][input.size() + 20][input.size() + 20][input.size() + 20];
        // initialize all cubes to inactive (.)
        for (int w = 0; w < grid.length; w++) {
            for (int z = 0; z < grid.length; z++) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid.length; x++) {
                        grid[w][z][y][x] = '.';
                    }
                }
            }
        }

        // initialize middle z-layer to input state
        for (int z = 0; z < grid.length; z++) {
            for (int y = 0; y < input.size(); y++) {
                for (int x = 0; x < input.get(0).length(); x++) {
                    char c = input.get(y).charAt(x);
                    // grid[middle w][middle z][middle y - half of input length][middle x - half
                    // input length]
                    grid[grid.length / 2 - input.size() / 2][grid.length / 2
                            - input.size() / 2][grid.length / 2 - input.size() / 2 + y][grid.length / 2
                            + x] = c;
                }
            }
        }

        for (int cycle = 1; cycle <= 6; cycle++) {
            char[][][][] copy = new char[grid.length][grid.length][grid.length][grid.length];
            for (int w = 0; w < grid.length; w++) {
                for (int z = 0; z < grid.length; z++) {
                    for (int y = 0; y < grid.length; y++) {
                        for (int x = 0; x < grid.length; x++) {
                            int activeNeighbors = countNeighbors2(grid, w, z, y, x);
                            if (grid[w][z][y][x] == '#') {
                                if (activeNeighbors == 2 || activeNeighbors == 3) {
                                    copy[w][z][y][x] = '#';
                                } else {
                                    copy[w][z][y][x] = '.';
                                }
                            } else if (grid[w][z][y][x] == '.') {
                                if (activeNeighbors == 3) {
                                    copy[w][z][y][x] = '#';
                                } else {
                                    copy[w][z][y][x] = '.';
                                }
                            }
                        }
                    }
                }

            }
            grid = copy;
        }

        int active = 0;
        for (int w = 0; w < grid.length; w++) {
            for (int z = 0; z < grid.length; z++) {
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid.length; x++) {
                        if (grid[w][z][y][x] == '#') {
                            active++;
                        }
                    }
                }
            }
        }

        System.out.println("Active cubes: " + active);
    }

    private static int countNeighbors1(char[][][] grid, int z, int y, int x) {
        int count = 0;

        for (int zi = -1; zi <= 1; zi++) {
            for (int yi = -1; yi <= 1; yi++) {
                for (int xi = -1; xi <= 1; xi++) {
                    if (z + zi >= 0 && y + yi >= 0 && x + xi >= 0) {
                        if (z + zi < grid.length && y + yi < grid.length && x + xi < grid.length) {
                            if (!(zi == 0 && yi == 0 && xi == 0)) {
                                // ifs verify two things:
                                // *the coordinate we're about to check is legal
                                // *we are not about to check our current location
                                if (grid[z + zi][y + yi][x + xi] == '#') {
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private static int countNeighbors2(char[][][][] grid, int w, int z, int y, int x) {
        int count = 0;

        for (int wi = -1; wi <= 1; wi++) {
            for (int zi = -1; zi <= 1; zi++) {
                for (int yi = -1; yi <= 1; yi++) {
                    for (int xi = -1; xi <= 1; xi++) {
                        if (w + wi >= 0 && z + zi >= 0 && y + yi >= 0 && x + xi >= 0) {
                            if (w + wi < grid.length && z + zi < grid.length && y + yi < grid.length
                                    && x + xi < grid.length) {
                                if (!(wi == 0 && zi == 0 && yi == 0 && xi == 0)) {
                                    // ifs verify two things:
                                    // *the coordinate we're about to check is legal
                                    // *we are not about to check our current location
                                    if (grid[w + wi][z + zi][y + yi][x + xi] == '#') {
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }
}
