package dk.cngroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Puzzle10 {
    public static void main(String[] args) throws IOException {
        String day = "10";
        var adaptersList= Utils.getLinesToIntegerList(day);

        solutionOne(adaptersList);
        solutionTwo(adaptersList);

    }

    private static void solutionTwo(final List<Integer> adapters) {
        Map<Integer, Long> knownWays = new HashMap<>();
        var max = adapters.stream()
                .mapToInt(v -> v)
                .max()
                .orElseThrow()
                + 3;
        // extend list
        adapters.add(0);
        adapters.add(max);
        List<Integer> sorted = adapters.stream().sorted().collect(Collectors.toList());

        System.out.println("second solution: " + findWay(0, sorted, knownWays));

    }

    private static Long findWay(final int index, final List<Integer> adapters, final Map<Integer, Long> knownWays) {
        // if we are at the end
        if (index == adapters.size() -1) {
            return 1L;
        }

        // when the number was already counted, it should be present in the map
        if (knownWays.containsKey(index)) {
            return knownWays.get(index);
        }

        Long result = 0L;

        for (int i = index + 1; i < adapters.size(); i++) {
            if (adapters.get(i) - adapters.get(index) <= 3) {
                result+= findWay(i, adapters, knownWays);
            }
        }

        knownWays.put(index, result);
        return result;
    }

    private static void solutionOne(final List<Integer> adaptersList) {
        List<Integer> adaptersSorted = adaptersList.stream().sorted().collect(Collectors.toList());
        var jolt1 = 0;
        var jolt3 = 1; // to plug the device (+3) at the end
        var index = -1; // to start from the wall :)
        var current = 0;

        while (index < adaptersSorted.size() - 1) {

            var next1 = current + 1;
            var next3 = current + 3;
            var next5 = current + 5;
            var nextValue = adaptersSorted.get(index+1);
            if (nextValue == next1) {
                jolt1++;
            } else if (nextValue == next3) {
                jolt3++;
            }

            index++;
            current = adaptersSorted.get(index);
        }

        System.out.println("first solution could be " + (jolt1 * jolt3));
    }
}
