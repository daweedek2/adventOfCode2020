package dk.cngroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class Puzzle18 {
    public static void main(String[] args) throws IOException {
        firstSolution();
        secondSolution();
    }

    private static void secondSolution() throws IOException {
        System.out.println("2: " + calculateSum((stack, value) -> !stack.equals("*") || !value.equals("+")));
    }

    private static void firstSolution() throws IOException {
        System.out.println("1: " + calculateSum((stack, value) -> true));
    }

    private static Long calculateSum(BiPredicate<String, String> precedence) throws IOException {
        return Utils.getFileLines("18").mapToLong(line -> evaluatePostfix(infixToPostfix(line, precedence))).sum();
    }

    private static Long evaluatePostfix(List<String> tokens) {
        var stack = new Stack<Long>();
        for (var token : tokens) {
            if (token.matches("\\d+")) {
                stack.push(Long.parseLong(token));
            } else {
                var rhs = stack.pop();
                var lhs = stack.pop();
                switch (token) {
                    case "+": stack.push(lhs + rhs);break;
                    case "*": stack.push(lhs * rhs);break;
                }
            }
        }
        return stack.pop();
    }

    private static List<String> infixToPostfix(String value, BiPredicate<String, String> precedence) {
        var output = new ArrayList<String>();
        var stack = new Stack<String>();
        var matcher = Pattern.compile("(\\d+|[+*()])").matcher(value.replaceAll("\\s+", ""));
        while (matcher.find()) {
            var token = matcher.group(1);
            if (token.matches("\\d+")) {
                output.add(token);
            } else if (token.matches("[+*]")) {
                while (!stack.isEmpty() && precedence.test(stack.peek(), token) && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }
}
