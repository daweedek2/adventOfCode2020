package dk.cngroup.year2020;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.subarray;

public class Puzzle16 {

    public static void main(String[] args) throws IOException {
        Rule[] rules;
        long[] myTicket;
        List<List<Long>> tickets;
        String s = Files.readString(Path.of("/Java/adventOfCode2020/puzzle16.txt"), StandardCharsets.ISO_8859_1);
        String[] input = s.split("\r\n\r\n");
        rules = stream(input[0].split("\r\n"))
                .map(sa -> readString(sa, "%s: %n-%n or %n-%n", Rule.class))
                .toArray(Rule[]::new);
        myTicket = stream(input[1].split("\n")[1].split(",")).mapToLong(Long::parseLong).toArray();
        String[] ticketStrings = input[2].split("\r\n");
        tickets = stream(subarray(ticketStrings, 1, ticketStrings.length)).map(sa -> stream(sa.split(",")).map(Long::parseLong).collect(toList())).collect(toList());
        solutionOne(tickets, rules);
        solutionTwo(tickets, rules, myTicket);
    }


    private static void solutionTwo(final List<List<Long>> tickets, final Rule[] rules, final long[] myTicket) {

        List<List<Long>> valid = tickets.stream().filter(t -> t.stream().allMatch(n -> stream(rules).anyMatch(r -> r.check(n)))).collect(toList());

        Multimap<Integer, Rule> ruleIndex = MultimapBuilder.hashKeys().arrayListValues().build();
        for (Rule r : rules) {
            for (int j = 0; j < valid.get(0).size(); j++) {
                int finalJ = j;
                if (valid.stream().allMatch(t -> r.check(t.get(finalJ)))) {
                    ruleIndex.put(j, r);
                }
            }
        }

        Optional<Map.Entry<Integer, Collection<Rule>>> rs;
        Set<Integer> indices = new HashSet<>();
        while ((rs = ruleIndex.asMap().entrySet().stream().filter(e -> e.getValue().size() == 1 && !indices.contains(e.getKey())).findAny()).isPresent()) {
            Map.Entry<Integer, Collection<Rule>> r = rs.get();
            int index = r.getKey();
            Rule rule = ((List<Rule>) r.getValue()).get(0);
            for (int i = 0; i < rules.length; i++) {
                Map.Entry<Integer, Collection<Rule>> t = new ArrayList<>(ruleIndex.asMap().entrySet()).get(i);
                if (t.getKey() != index) {
                    t.getValue().remove(rule);
                }
            }
            indices.add(index);
        }

        System.out.println(ruleIndex.asMap().entrySet().stream().filter(e -> e.getValue().stream().anyMatch(Rule::isDeparture)).mapToLong(e -> myTicket[e.getKey()]).reduce((a, b) -> a * b).getAsLong());

    }

    private static void solutionOne(final List<List<Long>> tickets, final Rule[] rules) {
        System.out.println(tickets.stream().flatMapToLong(t -> t.stream().filter(n -> stream(rules).noneMatch(r -> r.check(n))).mapToLong(e -> e)).sum());
    }

    public static class Rule {
        String name;
        long lower1;
        long upper1;
        long lower2;
        long upper2;

        public Rule(final String name, final long lower1, final long upper1, final long lower2, final long upper2) {
            this.name = name;
            this.lower1 = lower1;
            this.upper1 = upper1;
            this.lower2 = lower2;
            this.upper2 = upper2;
        }

        public boolean check(long val) {
            return (val >= lower1 && val <= upper1) || (val >= lower2 && val <= upper2);
        }

        public boolean isDeparture() {
            return name.startsWith("departure");
        }
    }

    static<T> T readString(String s, String pattern, Class<T> target) {
        List<Object> mappedObjs = new ArrayList<>();
        while (s.length() > 0) {
            if (pattern.length() > 1 && pattern.charAt(0) == '%') {
                int size = mappedObjs.size();
                switch (pattern.charAt(1)) {
                    case 'n':
                        mappedObjs.add(crunchNumber(s, pattern));
                        break;
                    case 'c':
                        mappedObjs.add(s.charAt(0));
                        break;
                    case 's':
                        mappedObjs.add(crunchString(s, pattern));
                        break;
                    default:
                        break;
                }
                if (mappedObjs.size() != size) {
                    s = s.substring(mappedObjs.get(size).toString().length());
                    pattern = pattern.substring(2);
                    continue;
                }
            }
            if (pattern.charAt(0) == s.charAt(0)) {
                s = s.substring(1);
                pattern = pattern.substring(1);
            } else {
                throw new IllegalStateException("Illegal crunch, pattern = " + pattern + " and s = " + s);
            }
        }
        try {
            return (T) Arrays.stream(target.getConstructors()).filter(c -> c.getParameterCount() == mappedObjs.size()).findAny().get()//.getConstructor(mappedObjs.stream().map(Object::getClass).toArray(Class[]::new))
                    .newInstance(mappedObjs.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        static long crunchNumber(String s, String pattern){
            return Long.parseLong(crunchString(s, pattern));
        }

        static String crunchString(String s, String pattern){
            return pattern.length() > 2 ? s.substring(0, s.indexOf(pattern.charAt(2))) : s;
        }
}
