package dk.cngroup.year2021;

import dk.cngroup.AbstractDay;
import dk.cngroup.NumGrid;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Created by dkostka on 12/2/2021.
 */
public class Day4 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day4 day = new Day4();
        day.solve();
    }

    @Override
    public String getDay() {
        return "4";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        var in = (String) puzzle;
        String[] split = in.split("\r\n\r\n");
        long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
        List<NumGrid> cards = IntStream.range(1, split.length).mapToObj(i -> split[i]).map(NumGrid::new).toList();
        for(long num : nums){
            for(NumGrid card : cards){
                if(markCard(card, num) && checkCard(card)){
                    return result(card, num);
                }
            }
        }
        return "";
    }

    private long result(NumGrid card, long num) {
        return card.sumExcept(-1) * num;
    }

    private boolean markCard(NumGrid card, long num){
        return card.replace(num, -1);
    }

    private boolean checkCard(NumGrid grid){
        long[][] card = grid.grid;
        for(long[] nums : card){
            if(Arrays.stream(nums).allMatch(n -> n==-1)){
                return true;
            }
        }
        out: for(int i = 0; i<card[0].length; i++){
            for(int j = 0; j<card[i].length; j++){
                if(card[j][i] != -1){
                    continue out;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        var in = (String) puzzle;
        String[] split = in.split("\r\n\r\n");
        long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
        List<NumGrid> cards = IntStream.range(1, split.length).mapToObj(i -> new NumGrid(split[i])).collect(Collectors.toCollection(ArrayList::new));
        for(long num : nums){
            for(int i = 0; i<cards.size(); i++){
                NumGrid card = cards.get(i);
                if(markCard(card, num) && checkCard(card)){
                    if(cards.size() == 1){
                        return result(card, num);
                    } else {
                        cards.remove(i);
                        i--;
                    }
                }
            }
        }
        return "";

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
