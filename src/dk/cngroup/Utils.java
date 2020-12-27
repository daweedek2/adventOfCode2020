package dk.cngroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static String getPathString(final String day) {
        return "/Java/adventOfCode2020/puzzle" + day + ".txt";
    }

    public static Path getPath(final String day) {
        return Path.of("/Java/adventOfCode2020/puzzle" + day + ".txt");
    }

    public static Integer[] getLinesToIntegerArray(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)))
                .map(s -> Integer.parseInt(s.trim()))
                .toArray(Integer[]::new);
    }

    public static Stream<String> getFileLines(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)));
    }

    public static List<Integer> getLinesToIntegerList(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)))
                .map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
    }

    public static Long[] getLinesToLongArray(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)))
                .map(s -> Long.parseLong(s.trim()))
                .toArray(Long[]::new);
    }

    public static String[] getLinesToStringArray(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)))
                .toArray(String[]::new);
    }

    public static List<String> getLinesToList(final String day) throws IOException {
        return Files.lines(Path.of(getPathString(day)))
                .collect(Collectors.toList());
    }

    public static String getString(final String day) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(getPathString(day)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        return stringBuilder.toString();
    }

    public static String getStringWithSingleLineSeparator(final String day) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(getPathString(day)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        return stringBuilder.toString();
    }

    public static char[][] get3DStringArray(final String day) throws IOException {
        String[] twoD = getLinesToStringArray(day);
        char[][] threeD = new char[twoD.length][twoD[0].length()];
        for (int i = 0; i < twoD.length; i++) {
            for (int j = 0; j < twoD[i].length(); j++) {
                threeD[i][j] = twoD[i].charAt(j);
            }
        }
        return threeD;
    }

    public static long[] getLongArray(final String s) {
        return new long[0];
    }
}
