package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day3 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day3 day = new Day3();
        day.solve();
    }

    @Override
    public String getDay() {
        return "3";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return null;
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        var in = Utils.getLinesToStringArray(getDay());
        String most = "";
        String least = "";

        for(int i = 0; i<in[0].length(); i++){
            if(moreZeros(in, i)){
                most+="0";
                least+="1";
            } else {
                most+="1";
                least+="0";
            }
        }
        return parseInt(most, 2) * parseInt(least, 2);
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        var in = Utils.dayStream("\r\n", getDay()).toList();
        return parseInt(findVal(in, true, 0).get(0), 2) * parseInt(findVal(in, false, 0).get(0), 2);
    }

    private List<String> findVal(List<String> in, boolean high, int pos) {
        List<String> res = new ArrayList<>(in);
        res.removeIf(e -> e.charAt(pos) == (!moreZeros(in.toArray(String[]::new), pos)^high ? '1' : '0'));
        if(res.size() == 1) return res;
        return findVal(res, high, pos+1);
    }

    private boolean moreZeros(String[] in, int i) {
        int ones = 0, zeros = 0;
        for(String s : in){
            if(s.charAt(i) == '1'){
                ones++;
            } else {
                zeros++;
            }
        }
        return ones<zeros;
    }
}
