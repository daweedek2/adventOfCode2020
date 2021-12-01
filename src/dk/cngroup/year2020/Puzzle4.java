package dk.cngroup.year2020;

import dk.cngroup.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Puzzle4 {
    private static final String DAY = "4";

    public static void main(String[] args) throws IOException {

        String content = Utils.getString(DAY);

        String[] passports = content.split("\n\\s*\n");

        long count1 = Arrays.stream(passports)
                .filter(Puzzle4::isValidPartOne)
                .count();
        long count2 = Arrays.stream(passports)
                .filter(Puzzle4::isValidPartTwo)
                .count();

        System.out.println(count1);
        System.out.println(count2);
    }

    private static boolean isValidPartTwo(final String passport) {
        if (!isValidPartOne(passport)) {
            return false;
        }

        String[] splitPassport = passport.split("[\n\r\\s]+");

        for (String s : splitPassport) {
            if (!isPartOfPassportValid(s)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isPartOfPassportValid(final String entry) {
        String[] codeAndValue = entry.split(":");
        String code = codeAndValue[0];
        String value = codeAndValue[1];
        int byrMin = 1920;
        int byrMax = 2002;
        int iyrMin = 2010;
        int iyrMax = 2020;
        int eyrMin = 2020;
        int eyrMax = 2030;
        String pidRegex = "[0-9]{9}";
        String hclRegex = "[#][a-f,0-9]{6}";

        switch (code) {
            case "pid": return validateWithRegex(value, pidRegex);
            case "byr": return validateYear(value, byrMin, byrMax);
            case "iyr": return validateYear(value, iyrMin, iyrMax);
            case "eyr": return validateYear(value, eyrMin, eyrMax);
            case "ecl": return validateEcl(value);
            case "hcl": return validateWithRegex(value, hclRegex);
            case "hgt": return validateHgt(value);
            default: return false;
        }
    }

    private static boolean validateHgt(final String value) {
        int heightValue = Integer.parseInt(value.replaceAll("[a-zA-Z]+", ""));
        if (!value.contains("in") && !value.contains("cm")) {
            return false;
        }

        if (value.contains("in")) {
            return heightValue <= 76 && heightValue >= 59;
        }

        if (value.contains("cm")) {
            return heightValue <= 193 && heightValue >= 150;
        }

        return false;
    }

    private static boolean validateEcl(final String value) {
        List<String> eyeColour = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return eyeColour.contains(value);
    }

    private static boolean validateYear(final String value, int min, int max) {
        if (!value.matches("[0-9]{4}")) {
            return false;
        }

        int year = Integer.parseInt(value);
        return year <= max && year >= min;
    }

    private static boolean validateWithRegex(final String value, final String regex) {
        return value.matches(regex);
    }

    private static boolean isValidPartOne(final String passport) {
        List<String> requiredFields = List.of("pid", "byr", "iyr", "eyr", "hcl", "hgt", "ecl");
        long count = requiredFields.stream()
                .filter(field -> passport.contains(field))
                .count();

        return count == 7;
    }


}
