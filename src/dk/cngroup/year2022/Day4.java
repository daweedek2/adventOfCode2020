package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.stream.Stream;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
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
        return (Utils.dayStream(getDay())).map(String::trim).map(s -> readString(s, "%n-%n,%n-%n", Assignment.class));
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        return ((Stream<Assignment>) getPuzzle())
                .filter(Assignment::contained).count();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        return ((Stream<Assignment>) getPuzzle())
                .filter(Assignment::overlap).count();
    }

    public record Assignment(long aStart, long aEnd, long bStart, long bEnd){
        public boolean contained(){
            return (aStart>=bStart && aEnd<=bEnd) || (bStart>=aStart && bEnd<=aEnd);
        }

        public boolean overlap(){
            return aStart <= bEnd && aEnd >= bStart;
        }
    }
}

