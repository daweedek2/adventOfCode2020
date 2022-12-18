package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;


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
        return Utils.getLinesToIntegerArray(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) {
        Integer[] input = (Integer[]) puzzle;
        int res = 0;
        for (int i = 1; i<input.length; i++) {
            if (input[i-1] < input[i]) {
                res++;
            }
        }
        return res;
    }

    @Override
    public Object partTwo(final Object puzzle) {
        Integer[] input = (Integer[]) puzzle;
        int res = 0;
        for(int i = 3; i<input.length; i++){
            if(input[i-3] + input[i-2] + input[i-1] < input[i] + input[i-2] + input[i-1]){
                res++;
            }
        }
        return res;
    }
}

