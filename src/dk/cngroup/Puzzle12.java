package dk.cngroup;

import java.io.IOException;

public class Puzzle12 {
    public static void main(String[] args) throws IOException {
        String day = "12";
        var instructions = Utils.getLinesToStringArray(day);
        firstSolution(instructions);
        secondSolution(instructions);
    }

    private static void firstSolution(final String[] instructions) {
        // aktualni svetove strany, na indexu 0 he aktualni smer
        var sides = new String[]{"E", "S", "W", "N"};
        // east jsou kladne, west jsou zaporne (jako na ose x)
        int eastWest = 0;
        // north jsou kladne, south jsou zaporne (jako na ose y)
        int northSouth = 0;

        // prochazim instrukce
        for (int i = 0; i < instructions.length; i++) {
            var code = instructions[i].substring(0,1);
            var value = Integer.parseInt(instructions[i].substring(1));

            // pokud je F -> code nastavim na aktualni smer a pak v dalsich ifech poresim o kolik se posune
            if (code.equals("F")) {
                code = sides[0];
            }

            if (code.equals("N")) {
                northSouth += value;
            } else if (code.equals("S")) {
                northSouth -= value;
            } else if (code.equals("W")) {
                eastWest -= value;
            } else if (code.equals("E")) {
                eastWest += value;
            } else if (code.equals("L") || code.equals("R")) {
                sides = changeDirection(sides, code, value);
            }
        }

        System.out.println("First solution is: " + (Math.abs(northSouth) + Math.abs(eastWest)));
    }

    private static void secondSolution(final String[] instructions) {
        // aktualni souradnice jakoby vektoru
        int x = 10;
        int y = 1;
        int eastWest = 0;
        int northSouth = 0;

        // prochazim kazdou instrukci
        for (int i = 0; i < instructions.length; i++) {
            var code = instructions[i].substring(0, 1);
            var value = Integer.parseInt(instructions[i].substring(1));

            // pokud je Forward -> vynasobim souradnice * value a prictu
            if (code.equals("F")) {
                eastWest += value * x;
                northSouth += value * y;
            }

            if (code.equals("N")) {
                y += value;
            } else if (code.equals("S")) {
                y -= value;
            } else if (code.equals("W")) {
                x -= value;
            } else if (code.equals("E")) {
                x += value;
            } else if (code.equals("L")) {
                // podle stupnu o kolik se ma otocit, tolikrat tocim doleva (napr 270/90 -> 3 krat tocim doleva)
                for (int j = 0; j < value/90; j++) {
                    int tmp = x;
                    x = - y;
                    y = tmp;
                }
            } else if (code.equals("R")) {
                // podle stupnu o kolik se ma otocit, tolikrat tocim doleva
                for (int k = 0; k < value/90; k++) {
                    int tmp = x;
                    x = y;
                    y = - tmp;
                }
            }
        }
        System.out.println("Second solution is: " + (Math.abs(northSouth) + Math.abs(eastWest)));
    }

    private static String[] changeDirection(String[] sides, final String code, final int value) {
        int index;
        // najdu si index, o kolik se mam posunout
        if (code.equals("L")) {
            // pokud tocim doleva, tak odecitam od 4ky (napr 270/90 = 3 - takze mam jit o 3 doleva od aktualniho (coz je vzdycky index 0)
            // 4-1 -> 1 -> takze novy aktualni smer musi byt na indexu 1)
            index = 4 - (value / 90);
        } else {
            index = (value / 90);
        }

        //pak posunu array aby zacinala novym indexem
        return shiftArray(sides, index);
    }

    private static String[] shiftArray(String[] array, final int index) {

        var temp = new String[4];
        for (int i = 0; i < 4; i++) {
            temp[i] = array[(index + i) % 4]; // pouzite modulo -> napr index 4 % 4 = 0 -> takze jsem zase na zacatku array
        }
        return temp;
    }
}
