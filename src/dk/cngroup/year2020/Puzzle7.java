package dk.cngroup.year2020;

import dk.cngroup.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Puzzle7 {
    private static final String DAY = "7";
    public static void main(String[] args) throws IOException {
        final String[] rules = Utils.getLinesToStringArray(DAY);
        Map<String, String[]> map1 = new HashMap<>();
        Map<String, String[]> map2 = new HashMap<>();
        Arrays.stream(rules).forEach(rule -> createMap(rule, map1, "shiny gold", false));
        Arrays.stream(rules).forEach(rule -> createMap(rule, map2, "shiny gold", true));

        System.out.println("Part one solution is: " +  getFirstSolution("shiny gold", map1));
        System.out.println("Part two solution is: " + getSecondSolution("shiny gold", map2));
     }

    private static int getSecondSolution(final String bag, final Map<String, String[]> map) {
        int total = 0;
        total+= getDetailCount(map.get(bag), map);
        return total;
    }

    private static int getDetailCount(final String[] detail, final Map<String, String[]> map) {
        int total = 0;
        if (detail[0] == null) {
            return 0;
        }

        for (String bag : detail) {

            var split = bag.split(" ");

            int value = Integer.parseInt(split[0]);
            String colorKey = split[1] + " " + split[2];

            if (split.length != 3) { // no other bags
                total += value;
            } else {
                total += value + value * getDetailCount(map.get(colorKey), map);
            }
        }
        return total;
    }

    private static int getFirstSolution(final String key, final Map<String, String[]> map) {
        int total = 0;
        for (Map.Entry<String,String[]> entry : map.entrySet()) {
            if (containsKey(entry.getValue(), key, map)) {
                total += 1;
            }
        }

        return total;
    }

    private static boolean containsKey(final String[] entries, final String key, final Map<String, String[]> map) {
        if (entries[0] == null) {
            return false;
        }

        for (String entry : entries) {
            if (entry.equals(key)) {
                return true;
            } else {
                if (containsKey(map.get(entry), key, map)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void createMap(final String rule, final Map<String, String[]> map, String key, final boolean withNumbers) {
        String[] bagAndDetails = rule.split(" contain ");
        String[] bag = bagAndDetails[0].split(" ");
        String bagKey = bag[0] + " " + bag[1];
        String[] details = bagAndDetails[1].split(", ");

        String[] preparedDetails = new String[details.length];
        for (int i = 0; i < details.length; i++) {
            String[] singleDetail = details[i].split(" ");
            if (singleDetail.length == 4) {
                preparedDetails[i] = getBagDetail(singleDetail, withNumbers);
            }
        }

        map.put(bagKey, preparedDetails);
    }

    private static String getBagDetail(final String[] singleDetail, final boolean withNumbers) {
        String number = singleDetail[0];
        String color = String.join(" ", singleDetail[1], singleDetail[2]);
        return withNumbers ? String.join(" ", number, color) : color;
    }
}
