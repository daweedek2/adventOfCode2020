package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.LongStream;


public class Day1 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day1 day = new Day1();
        day.solve();
    }

    @Override
    public String getDay() {
        return "1";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return input().max().getAsLong();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        long[] nums = input().sorted().toArray();
        return nums[nums.length-1] + nums[nums.length-2] + nums[nums.length-3];
    }

    private LongStream input () throws IOException {
        return Arrays.stream(((String) getPuzzle()).split("\r\n\r\n"))
                .mapToLong(s -> Arrays.stream(s.split("\r\n"))
                        .map(String::trim)
                        .mapToLong(Long::parseLong).sum());
    }
}

