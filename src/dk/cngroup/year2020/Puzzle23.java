package dk.cngroup.year2020;

import com.google.common.collect.Streams;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

public class Puzzle23 {
    public static void main(String[] args) throws IOException {
        System.out.println(getSolution(true));
        System.out.println(getSolution(false));
    }

    private static long getSolution(boolean part1) throws IOException {
        int[] input = Utils.getStringWithSingleLineSeparator("23").chars().map(Character::getNumericValue).toArray();
        CircularLinkedList cups = new CircularLinkedList(
                Streams.concat(stream(input), part1 ? IntStream.empty() : IntStream.rangeClosed(10, 1000000)
                ).toArray());
        for(int i = 0; i<(part1 ? 100 : 10000000); i++){
            int current = cups.current();
            int j;
            CircularLinkedList.Node next = cups.currentNode().next;
            CircularLinkedList.Node last = next.next.next;
            for(j = current - 2 + cups.size(); j>0; j--){
                int n = j % cups.size() + 1;
                if(next.value != n && next.next.value != n && last.value != n){
                    break;
                }
            }
            int d = j % cups.size() + 1;
            cups.insertAfter(next, last, d);
            cups.next();
        }
        cups.setCurrent(1);
        if(part1) return parseLong(stream(cups.next(8)).mapToObj(Integer::toString).collect(Collectors.joining()));
        int[] next = cups.next(2);
        return (long)next[0] * next[1];
    }

    public static class CircularLinkedList {
        public class Node {
            public int value;
            public Node prev;
            public Node next;
        }

        private Node current;
        private final Map<Integer, Node> valueMap = new HashMap<>();

        public CircularLinkedList(int[] elements) {
            Node prev = null;
            Node first = null;
            for (int element : elements) {
                Node n = new Node();
                n.value = element;
                n.prev = prev;
                valueMap.put(element, n);
                if (prev != null) prev.next = n;
                else first = n;
                prev = n;
            }
            prev.next = first;
            first.prev = prev;
            this.current = first;
        }

        public int current() {
            return current.value;
        }

        public void next() {
            current = current.next;
        }

        public int[] next(int n) {
            int[] arr = new int[n];
            Node node = current;
            for (int i = 0; i < n; i++) {
                node = node.next;
                arr[i] = node.value;
            }
            return arr;
        }

        public Node currentNode() {
            return current;
        }

        public void insertAfter(Node s1, Node s2, int dest) {
            Node d = valueMap.get(dest);
            Node oldNext = d.next;
            s1.prev.next = s2.next;
            s2.next.prev = s1.prev;
            d.next = s1;
            s2.next = oldNext;
            oldNext.prev = s2;
            s1.prev = d;
        }

        public int size() {
            return valueMap.size();
        }

        public int[] toArray() {
            return next(size());
        }

        public void setCurrent(int value) {
            current = valueMap.get(value);
        }
    }
}
