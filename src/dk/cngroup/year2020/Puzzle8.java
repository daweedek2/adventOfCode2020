package dk.cngroup.year2020;

import dk.cngroup.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzle8 {
    public static void main(String[] args) throws IOException {
        String day = "8";
        var instructions = Utils.getLinesToList(day);
        var instrArray = Utils.getLinesToStringArray(day);
        System.out.println("Part one: " + getAccCountPartOne(instructions));
        System.out.println("Part two: " + getSecondSolution(instrArray));
    }

    private static int getSecondSolution(final String[] instrArrayA) {

        int acc = 0;

        for (int i = 0; i < instrArrayA.length; i++) {
            var instArrayAdapted = getCopyOfArray(instrArrayA);
            var op = instArrayAdapted[i].split(" ")[0];
            var arg = instArrayAdapted[i].split(" ")[1];
            if (op.equals("nop")) {
                instArrayAdapted[i] = "jmp " + arg;
            } else if (op.equals("jmp")) {
                instArrayAdapted[i] = "nop " + arg;
            }

            int t = 0;
            int acc2 = 0;
            int currentPosition = 0;
            List<Integer> alreadyVisited = new ArrayList<>();

            while (currentPosition < instArrayAdapted.length && t<500) {
                t++;
                alreadyVisited.add(currentPosition);
                var split = instArrayAdapted[currentPosition].split(" ");
                var cmd = split[0];
                var sign = split[1].charAt(0);
                var number = split[1].replaceAll("[^\\d.]", "");

                if (cmd.equals("nop")) {
                    currentPosition++;
                } else if (cmd.equals("acc")) {
                    currentPosition++;
                    acc2 = getNewValue(acc2, sign, number);
                } else if (cmd.equals("jmp")) {
                    currentPosition = getNewValue(currentPosition, sign, number);
                }

                if (currentPosition == instArrayAdapted.length) {
                    System.out.println(acc2);
                    acc = acc2;
                    break;
                }
            }

        }

        return acc;

    }

    private static String[] getCopyOfArray(final String[] old) {
        String[] a = new String[old.length];
        for (int i = 0; i < old.length; i++ ) {
            a[i] = old[i];
        }

        return a;
    }

    private static int getAccCountPartOne(final List<String> intructions) {
        int acc = 0;
        int currentPosition = 0;
        List<Integer> alreadyVisited = new ArrayList<>();

        while (!alreadyVisited.contains(currentPosition)) {
            alreadyVisited.add(currentPosition);
            var split = intructions.get(currentPosition).split(" ");
            var cmd = split[0];
            var sign = split[1].charAt(0);
            var number = split[1].replaceAll("[^\\d.]", "");

            if (cmd.equals("nop")) {
                currentPosition++;
            } else if (cmd.equals("acc")) {
                currentPosition++;
                acc = getNewValue(acc, sign, number);
            } else if (cmd.equals("jmp")) {
                currentPosition = getNewValue(currentPosition, sign, number);
            }
        }

        return acc;
    }

    private static int getNewValue(int value, final char sign, final String number) {
        if (sign == '+') {
            return value += Integer.parseInt(number);
        } else if (sign == '-') {
            return value -= Integer.parseInt(number);
        } else return 0;
    }
}
