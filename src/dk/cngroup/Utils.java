package dk.cngroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.IntStream.range;

public class Utils {
    private static final String YEAR = "2022";
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
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        return stringBuilder.toString();
    }

    public static char[][] dayGrid(final String day) throws IOException {
        return getFileLines(day).map(String::toCharArray).toArray(char[][]::new);
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

    public static Stream<String> dayStream(final String day) throws IOException {
        return Arrays.stream(getString(day).split("\r\n"));
    }

    public static LongStream longStream(final String delimiter, final String day) throws IOException {
        return Arrays.stream(getString(day).split(delimiter))
                .filter(e -> !e.isEmpty()).map(e -> e.replace("\n", "").trim()).mapToLong(Long::parseLong);
    }

    public static <T> T readString(String s, String pattern, Class<T> target) {
        List<Object> mappedObjs = new ArrayList<>();
        while (s.length() > 0) {
            if (pattern.length() > 1 && pattern.charAt(0) == '%') {
                int size = mappedObjs.size();
                switch ( pattern.charAt( 1 ) )
                {
                    case 'n' -> mappedObjs.add(crunchNumber(s, pattern));
                    case 'c' -> mappedObjs.add(s.charAt(0));
                    case 's' -> mappedObjs.add(crunchString(s, pattern));
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
            verify(target.getConstructors().length > 0, "Class "+target+" has no constructor!");
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

    public static int i(long n) {
        return Math.toIntExact(n);
    }


    public static void verify(boolean b) {
        verify(b, "Something went wrong");
    }

    public static void verify(boolean b, String message) {
        if(!b) {
            throw new IllegalStateException(message);
        }
    }

    public static<A> Stream<Pair<A, A>> connectedPairs(List<A> l) {
        return range(1, l.size()).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }

    public static<A> Stream<Pair<A, A>> pairs(List<A> l) {
        return range(1, l.size()/2).map(i -> i + ((i-1)*2)).mapToObj(i -> Pair.of(l.get(i-1), l.get(i)));
    }

    public static<A> Stream<Pair<A, A>> allPairs(List<A> l) {
        return range(0, l.size()).boxed().flatMap(i -> range(i+1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
    }

    public static<A, B> Stream<Pair<A, B>> allPairs(List<A> l1, List<B> l2) {
        return range(0, l1.size()).boxed().flatMap(i -> l2.stream().map(b -> new Pair<>(l1.get(i), b)));
    }

    public static LongStream dayNumberStream() throws IOException {
        return dayNumberStream("\r\n");
    }

    public static LongStream dayNumberStream(String delimiter) throws IOException {
        return dayStream(delimiter).filter(e -> !e.isEmpty()).map(e -> e.replace("\n", "").trim()).mapToLong(Long::parseLong);
    }

    public static long binarySearch(Function<Long, Long> testFunction, long target, long low, long high) {
        while(true) {
            if(low == high) return low;
            long size = (high - low) / 3;
            long l1 = low + size;
            long res1 = testFunction.apply(l1);
            long diff1 = Math.abs(res1 - target);
            if(diff1 == 0) return l1;
            long l2 = l1 + size;
            long res2 = testFunction.apply(l2);
            long diff2 = Math.abs(res2 - target);
            if(diff2 == 0) return l2;
            if(diff1 <= diff2) high = l2-1;
            if (diff1 >= diff2) low = l1+1;
        }
    }

    public static long binarySearch(Function<Long, Long> testFunction, long low, long high) {
        return binarySearch(testFunction, 0, low, high);
    }

    public static long binarySearch(Function<Long, Long> testFunction) {
        return binarySearch(testFunction, 0, 0, Long.MAX_VALUE);
    }
}
