package dk.cngroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;

public class Utils {
    private static final String YEAR = "2021";
    public static String getPathString( final String day) {
        return String.format("/Java/adventOfCode2020/src/dk/cngroup/year%s/puzzles/puzzle%s.txt", YEAR, day);
    }

    public static Path getPath(final String day) {
        return Path.of(getPathString(day));
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
//        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
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

    public static Stream<String> dayStream(final String delimiter, final String day) throws IOException {
        return Arrays.stream(getString(day).split(delimiter));
    }

    public static <T> T readString(String s, String pattern, Class<T> target) {
        List<Object> mappedObjs = new ArrayList<>();
        while (s.length() > 0) {
            if (pattern.length() > 1 && pattern.charAt(0) == '%') {
                int size = mappedObjs.size();
                switch (pattern.charAt(1)) {
                    case 'n':
                        mappedObjs.add(crunchNumber(s, pattern));
                        break;
                    case 'c':
                        mappedObjs.add(s.charAt(0));
                        break;
                    case 's':
                        mappedObjs.add(crunchString(s, pattern));
                        break;
                    default:
                        break;
                }
                if (mappedObjs.size() != size) {
                    s = s.substring(mappedObjs.get(size).toString().length());
                    pattern = pattern.substring(2);
                    continue;
                }
            }
            if (pattern.charAt(0) == s.charAt(0)) {
                s = s.substring(1);
                pattern = pattern.substring(1);
            } else {
                throw new IllegalStateException("Illegal crunch, pattern = " + pattern + " and s = " + s);
            }
        }
        try {
            checkState(target.getConstructors().length > 0, "Class "+target+" has no constructor!");
            return (T) Arrays.stream(target.getConstructors()).filter(c -> c.getParameterCount() == mappedObjs.size()).findAny().get()//.getConstructor(mappedObjs.stream().map(Object::getClass).toArray(Class[]::new))
                    .newInstance(mappedObjs.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static long crunchNumber(String s, String pattern) {
        return Long.parseLong(crunchString(s, pattern));
    }

    static String crunchString(String s, String pattern) {
        return pattern.length() > 2 ? s.substring(0, s.indexOf(pattern.charAt(2))) : s;
    }
}
