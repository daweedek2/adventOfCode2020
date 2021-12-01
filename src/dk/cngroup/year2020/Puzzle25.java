package dk.cngroup.year2020;

public class Puzzle25 {
    public static void main(String[] args) {
        Puzzle25 puzzle25 = new Puzzle25();
        System.out.println(puzzle25.part1());
        System.out.println(puzzle25.part2());

    }

    public Object part1() {
        long cardPublicKey = 10212254;
        long doorPublicKey = 12577395;
        long value = 1;
        for(int loopSize = 1; true; loopSize++) {
            value = transform(value, loopSize-1, loopSize, 7);
            if(value == cardPublicKey) {
                return transform(1, 0, loopSize, doorPublicKey);
            }
        }
    }

    public long transform (long value, int start, int loopSize, long subjectNumber){
        for (int i = start; i < loopSize; i++) {
            value *= subjectNumber;
            value %= 20201227;
        }
        return value;
    }

    public Object part2() {
        return "That's all folks";
    }
}
