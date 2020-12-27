package dk.cngroup;

import java.io.IOException;

public class Puzzle11 {
    public static void main(String[] args) throws IOException {
        firstSolution();
        secondSolution();
    }

    private static void secondSolution() throws IOException {
        String day = "11";
        char[][] map = Utils.get3DStringArray(day);
        int currentChangedSeats = -1;

        var mapCopy = getCopy(map);

        while (currentChangedSeats != 0) {
            map = getCopy(mapCopy);
            currentChangedSeats = 0;
            for (int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 'L' && getCountOfOccupiedSeatsPartTwo(i, j, map) == 0) {
                        mapCopy[i][j] = '#';
                        currentChangedSeats++;
                    } else if (map[i][j] == '#' && getCountOfOccupiedSeatsPartTwo(i, j, map) >= 5) {
                        mapCopy[i][j] = 'L';
                        currentChangedSeats++;
                    }
                }
            }
        }
        System.out.println("first solution: " + getTotalOccupiedSeats(mapCopy));
    }

    private static void firstSolution() throws IOException {
        String day = "11";
        char[][] map = Utils.get3DStringArray(day);
        int currentChangedSeats = -1;

        var mapCopy = getCopy(map);

        while (currentChangedSeats != 0) {
            map = getCopy(mapCopy);
            currentChangedSeats = 0;
            for (int i = 0; i < map.length; i++) {
                for(int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == 'L' && getCountOfOccupiedSeats(i, j, map) == 0) {
                        mapCopy[i][j] = '#';
                        currentChangedSeats++;
                    } else if (map[i][j] == '#' && getCountOfOccupiedSeats(i, j, map) >= 4) {
                        mapCopy[i][j] = 'L';
                        currentChangedSeats++;
                    }
                }
            }
        }
        System.out.println("first solution: " + getTotalOccupiedSeats(mapCopy));
    }

    private static char[][] getCopy(final char[][] map) {
        var copy = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                copy[i][j] = map[i][j];
            }
        }
        return copy;
    }

    private static int getTotalOccupiedSeats(final char[][] map) {
        int total = 0;

        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '#') {
                total++;
                }
            }
        }
        return total;
    }

    private static int getCountOfOccupiedSeats(final int i, final int j, final char[][] map) {
        int occupiedSeats = 0;

        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                if (k >= 0 && k < map.length && l >= 0 && l < map[0].length) {
                    if (k==i && j==l) {
                        //do nothing
                    } else {
                        if (isOccupied(map[k][l])) {
                            occupiedSeats++;
                        }
                    }
                }
            }
        }

        return occupiedSeats;
    }

    private static int getCountOfOccupiedSeatsPartTwo(final int i, final int j, final char[][] map) {
        int occupiedSeats = 0;
        int xx = 0;
        int yy = 0;

        int[] x = {-1,0,1};
        int[] y = {-1,0,1};




        for (int k: x) {
            for (int l : y) {
                if (k == 0 && l == 0) {
                    //do nothing
                } else {
                    xx = k + i;
                    yy = l + j;
                    while (xx >= 0 && xx < map.length && yy >= 0 && yy < map[0].length && map[xx][yy] == '.') {
                        xx = xx + k;
                        yy = yy + l;
                    }
                    if (xx >= 0 && xx < map.length && yy >= 0 && yy < map[0].length && isOccupied(map[xx][yy])) {
                        occupiedSeats++;
                    }
                }
            }
        }

        return occupiedSeats;
    }

    private static boolean isOccupied(final char c) {
        return c == '#';
    }
}
