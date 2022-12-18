package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.*;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day6 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day6 day = new Day6();
        day.solve();
    }

    @Override
    public String getDay() {
        return "6";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        String s = ((String) getPuzzle());
        for(int i = 0; i<s.length(); i++){
            Set<Integer> chars = Set.copyOf(s.substring(i, i+4).chars().mapToObj(e -> e).toList());
            if(chars.size() == 4) return i+4;
        }
        return "";
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        String s = ((String) getPuzzle());
        for(int i = 0; i<s.length(); i++){
            Set<Integer> chars = Set.copyOf(s.substring(i, i+14).chars().mapToObj(e -> e).toList());
            if(chars.size() == 14) return i+14;
        }
        return "";
    }
}

