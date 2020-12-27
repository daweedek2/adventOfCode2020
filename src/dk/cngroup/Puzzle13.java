package dk.cngroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle13 {
    public static void main(String[] args) throws IOException {
        String day = "13";

        String[] input = Utils.getLinesToStringArray(day);
        var timestamp = Integer.parseInt(input[0]);
        String[] buses = input[1].replaceAll(",x", "").split(",");
        Map<Integer, Integer> busesWithTimestamps = getBusesWithTimestamps(input[1]);

        firstSolution(timestamp, buses);

        var inputList = Utils.getLinesToList(day);
        secondSolution(inputList);
    }

    private static Map<Integer, Integer> getBusesWithTimestamps(final String buses) {
        String[] split = buses.split(",");
        Map<Integer, Integer> schedule = new HashMap<>();

        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                schedule.put(Integer.parseInt(split[i]), i);
            }
        }

        return schedule;
    }

    private static void secondSolution(final List<String> inputStrs) {
        List<Integer> busIDs = new ArrayList<>();
        boolean complete = false;
        long t = 0;
        int largestIndex = 0;
        for(String id : inputStrs.get(1).split(","))
        {
            if(id.equals("x"))
                busIDs.add(0);
            else
                busIDs.add(Integer.parseInt(id));

            if(busIDs.get(largestIndex) < busIDs.get(busIDs.size() - 1))
            {
                largestIndex = busIDs.size() - 1;
            }
        }

        while(!complete)
        {
            t += busIDs.get(largestIndex);
            complete = true;
            for(int i = 0; i < busIDs.size(); i++)
            {
                if(busIDs.get(i) == 0)
                    continue;

                if((t - largestIndex + i) % busIDs.get(i) != 0)
                {
                    complete = false;
                    break;
                }
            }

            if (t%10000 == 0 && t>0) System.out.println(t- largestIndex);
        }

        System.out.println(t - largestIndex);

    }


    private static void firstSolution(final int timestamp, final String[] buses) {
        Map<Integer, Integer> departs = new HashMap<>();
        for (String bus : buses) {
            int busMinutes = Integer.parseInt(bus);

            for (int i = 0; i < timestamp + 100; i++) {
                int depart = i*busMinutes;
                if (depart > timestamp) {
                    departs.put(busMinutes, depart);
                    break;
                }
            }
        }

        var min = departs.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get();

        var id = min.getKey();
        var time = min.getValue() - timestamp;

        System.out.println("nearest one is: " + (id * time));

    }
}
