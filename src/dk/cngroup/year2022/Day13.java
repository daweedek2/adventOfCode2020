package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Either;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day13 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day13 day = new Day13();
        day.solve();
    }

    @Override
    public String getDay() {
        return "13";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getString(getDay());
    }


    @Override
    public Object partOne(final Object puzzle) throws IOException {
        var in = Arrays.stream(((String) getPuzzle()).split("\r\n\r\n")).map(s -> s.split("\r\n")).toArray(String[][]::new);
        return range(0, in.length).filter(i -> compare(node(in[i][0]), node(in[i][1])).orElse(false)).map(i -> i+1).sum();
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        var in = Arrays.stream((((String) getPuzzle())+"\r\n[[2]]\r\n[[6]]").replace("\r\n\r\n", "\r\n").split("\r\n"))
                .map(this::node)
                .sorted((a, b) -> compare(a, b).map(c -> c ? -1 : 1).orElse(0))
                .toList();
        return (in.indexOf(node(node(node(2)))) + 1) * (in.indexOf(node(node(node(6)))) + 1);
    }

    public record Node (Either<List<Node>, Integer> value) {}

    private Node node(String s) {
        return node(s, findLevels(s));
    }

    private int[] findLevels(String str) {
        AtomicInteger l = new AtomicInteger();
        return str.chars().map(c -> l.addAndGet(c == '[' ? 1 : c == ']' ? -1 : 0)).toArray();
    }

    private Node node(String s, int[] levels) {
        if(s.charAt(0) >= '0' && s.charAt(0) <= '9') return node(parseInt(s));
        if(s.equals("[]")) return node(List.of());
        int[] commas = range(0, levels.length).filter(i -> i == 0 || i == levels.length - 1 || levels[i] == levels[0] && s.charAt(i) == ',').toArray();
        return node(range(1, commas.length).mapToObj(i -> node(s.substring(commas[i-1]+1, commas[i]))).toList());
    }

    private Optional<Boolean> compare(Node a, Node b) {
        if(a.value.isB() && b.value.isB()) {
            int na = a.value.getB();
            int nb = b.value.getB();
            if(na < nb) return of(true);
            else if(na > nb) return of(false);
            else return empty();
        } else if(a.value.isA() && b.value.isA()) {
            List<Node> na = a.value.getA();
            List<Node> nb = b.value.getA();
            if(na.isEmpty() && !nb.isEmpty()) return of(true);
            else if(!na.isEmpty() && nb.isEmpty()) return of(false);
            else if(na.isEmpty() && nb.isEmpty()) return empty();
            else return compare(na.get(0), nb.get(0)).or(() -> compare(node(na.subList(1, na.size())), node(nb.subList(1, nb.size()))));
        }
        else if(a.value.isA()) return compare(a, node(b));
        else return compare(node(a), b);
    }

    private Node node(List<Node> nodes) {
        return new Node(new Either<>(nodes, null));
    }

    private Node node(int n) {
        return new Node(new Either<>(null, n));
    }

    private Node node(Node n) {
        return node(List.of(n));
    }
}

