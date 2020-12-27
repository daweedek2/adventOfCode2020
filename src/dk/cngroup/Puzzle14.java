package dk.cngroup;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Puzzle14 {
    public static void main(String[] args) throws IOException {
        String day = "14";
        String[] data = Utils.getLinesToStringArray(day);

        solutionOne(data);
        solutionTwo(data);
        partTwo();
    }

    private static void partTwo() throws IOException {
        List<String> input = Utils.getLinesToList("14");
        String[] mask = null;
        Map<BigInteger, Integer> memory = new HashMap<>();
        Pattern p = Pattern.compile("\\d+");

        for (String s : input) {
            String[] lineParts = s.split(" = ");
            if ("mask".equals(lineParts[0])) {
                mask = lineParts[1].split("");
            } else {
                Matcher m = p.matcher(lineParts[0]);
                m.find();
                int address = Integer.parseInt(m.group());
                String[] binaryAddress = String.format("%36s", Integer.toBinaryString(address)).replace(" ", "0").split("");
                Set<String[]> addresses = applyPt2Mask(mask, binaryAddress);
                addresses.stream()
                        .map(sa -> String.join("", sa))
                        .map(binStr -> new BigInteger(binStr, 2))
                        .forEach(adr -> memory.put(adr, Integer.valueOf(lineParts[1])));
            }
        }
        BigInteger sum = memory.values().stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println("Part 2: " + sum);
    }

    private static Set<String[]> applyPt2Mask(String[] mask, String[] binaryAddress) {
        List<Integer> xIndexes = new ArrayList<>();
        for (int i = 0; i < mask.length; i++) {
            switch (mask[i].toUpperCase()) {
                case "X":
                    xIndexes.add(i);
                case "1":
                    binaryAddress[i] = mask[i];
                    break;
                case "0":
                    break;
                default:
                    throw new RuntimeException("something went wrong");
            }
        }
        //now handle x's 1000x0x01x
        Set<String[]> permutations = new HashSet<>();
        permutations.add(binaryAddress);
        for (int index : xIndexes) {
            //loop through our list of current permutations, and replace each X with its two permutations of 0 & 1
            permutations = permutations.stream()
                    .flatMap(a -> {
                        //using flat map to make each array into 2 different ones
                        String[] scenario0 = Arrays.stream(a).toArray(String[]::new);
                        scenario0[index] = "0";
                        String[] scenario1 = a;
                        scenario1[index] = "1";
                        return Stream.of(scenario0, scenario1);
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<>(permutations);
    }

    private static void solutionTwo(final String[] data) {
        Map<String, List<BigInteger>> map = new HashMap<>();
        int result = 0;
        String mask = "";
        for (String line : data) {
            if (line.contains("mask")) {
                mask = line.split(" = ")[1];
            }
            else if (line.contains("mem")) {
                List<Integer> values = new ArrayList<>();
                String address = line.split(" = ")[0].replaceAll("[^\\d.]", "");
                String binValue = convertToBinary(line.split(" = ")[1]);
                BigInteger originalInt = new BigInteger(binValue, 2);
                map.put(address, getValues(mask, binValue));
            }
        }
    }

    private static List<BigInteger> getValues(final String mask, final String binValue) {
        List<BigInteger> values = new ArrayList<>();
        StringBuilder temp = new StringBuilder(binValue);

        for (int i = 0; i < mask.length();  i++) {
            if (mask.charAt(i) == '1') {
                temp.setCharAt(i, mask.charAt(i));
            } else if (mask.charAt(i) == '0') {
                // do nothing
            } else if (mask.charAt(i) == 'X') {

            }
        }

        return Collections.EMPTY_LIST;

    }

    private static void solutionOne(final String[] data) {
        Map<String, BigInteger> map = new HashMap<>();
        int result = 0;
        String mask = "";
        for (String line : data) {
            if (line.contains("mask")) {
                mask = line.split(" = ")[1];
            }
            else if (line.contains("mem")) {
                String address = line.split(" = ")[0].replaceAll("[^\\d.]", "");
                String binValue = convertToBinary(line.split(" = ")[1]);
                BigInteger originalInt = new BigInteger(binValue, 2);
                String maskedValue = convertWithMask(mask, binValue);
                BigInteger maskedInt = new BigInteger(maskedValue, 2);
                map.put(address, maskedInt);
            }
        }

        System.out.println(map.values().stream().mapToLong(v -> v.longValue()).sum());
    }

    private static String convertWithMask(final String mask, final String binValue) {
        StringBuilder temp = new StringBuilder(binValue);
        for (int i = 0; i < mask.length();  i++) {
            if (mask.charAt(i) != 'X') {
                temp.setCharAt(i, mask.charAt(i));
            }
        }

        return temp.toString();
    }

    private static String convertToBinary(final String s) {
        StringBuilder t = new StringBuilder("000000000000000000000000000000000000");
        int a = Integer.parseInt(s);
        var result = Integer.toBinaryString(a);

        t.replace(36-result.length(), 36, result);




        return t.toString();
    }
}
