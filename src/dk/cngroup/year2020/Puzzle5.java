package dk.cngroup.year2020;

import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzle5 {
    private static final String DAY = "5";

    public static void main(String[] args) throws IOException {
        final String[] keys = Utils.getLinesToStringArray(DAY);

        int maxId = 0;
        int lowIndex = 0;
        int maxIndex = 127;
        int lowSeat = 0;
        int maxSeat = 7;

        var listOfIds = getAllIds();

        for (String key : keys) {
            int currentId = getCurrentId(lowIndex, maxIndex, lowSeat, maxSeat, key);
            listOfIds.remove(Integer.valueOf(currentId));
            if (currentId > maxId) {
                maxId = currentId;
            }
        }

        listOfIds.forEach(System.out::println);

        System.out.println("Part one: " + maxId);
    }

    private static int getCurrentId(final int lowIndex, final int maxIndex, final int lowSeat, final int maxSeat, final String key) {
        int row = resolve(key.substring(0, 7), lowIndex, maxIndex);
        int seat = resolve(key.substring(7, 10), lowSeat, maxSeat);
        return getId(row, seat);
    }

    private static List<Integer> getAllIds() {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 8; j++) {
                ids.add(getId(i, j));
            }
        }

        return ids;
    }

    private static int getId(final int row, final int seat) {
        return row * 8 + seat;
    }

    private static int resolve(final String key, final int low, final int high) {
        int min = low;
        int max = high;
        int availables = high + 1;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch == 'F' || ch == 'L') {
                max = max - availables / 2;
            }
            if (ch == 'B' || ch == 'R') {
                min  = min + availables / 2;
            }
            availables /= 2;
        }
        return min;
    }
}
