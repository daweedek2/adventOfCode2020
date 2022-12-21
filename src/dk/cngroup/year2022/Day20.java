package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.CircularList;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.LongStream;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day20 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day20 day = new Day20();
        day.solve();
    }

    @Override
    public String getDay() {
        return "20";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.dayNumberStream(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return solve(1, 1);
    }


    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        return solve(811589153L, 10);
    }

    public long solve(long multiplier, int times) throws IOException {
        CircularList in = new CircularList(((LongStream) getPuzzle()).map(l -> l * multiplier).toArray());
        var nodes = new ArrayList<>(in.valueMap);
        for(int j = 0; j<times; j++) {
            for (int i = 0; i < nodes.size(); i++) {
                var toMove = nodes.get(i);
                var moveTo = toMove.move(toMove.value, nodes.size());
                in.insertAfter(toMove, moveTo);
            }
        }
        in.setCurrent(nodes.stream().filter(n -> n.value == 0).findAny().get());
        long[] nums = in.next(3000);
        return nums[1000-1] + nums[2000-1] + nums[3000-1];
    }
}

