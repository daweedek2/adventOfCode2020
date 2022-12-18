package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.*;

import static dk.cngroup.Utils.readString;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day7 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day7 day = new Day7();
        day.solve();
    }

    @Override
    public String getDay() {
        return "7";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getLinesToList(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        List<String> commands = ((List<String>) getPuzzle());
        Node root = findChildren(commands);
        return sumSize(root);
    }

    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<String> commands = ((List<String>) getPuzzle());
        Node root = findChildren(commands);
        return sumSize(root, root.size()).stream().mapToLong(e -> e).min().getAsLong();
    }

    public record Node(Map<String, Node> children, long size){}
    public record File(long size, String name){}

    int i = 1;
    public Node findChildren(List<String> commands) {
        Map<String, Node> children = new HashMap<>();
        for(; i<commands.size(); i++) {
            String c = commands.get(i);
            if (c.charAt(0) == '$') {
                String command = c.substring(2);
                if (command.startsWith("cd")) {
                    String folder = command.substring(3);
                    if (folder.equals("..")) break;
                    i++;
                    children.put(folder, findChildren(commands));
                }
            } else {
                if(c.startsWith("dir")) {
                    String dirName = c.substring(4);
                    children.put(dirName, new Node(new HashMap<>(), 0));
                } else {
                    File f = readString(c, "%n %s", File.class);
                    children.put(f.name, new Node(new HashMap<>(), f.size));
                }
            }
        }
        return new Node(children, children.values().stream().mapToLong(Node::size).sum());
    }

    public long sumSize(Node n) {
        long total = 0;
        for(Node node : n.children.values()) {
            if(node.size<=100000 && !node.children.isEmpty()) {
                total+=node.size;
            }
            total += sumSize(node);
        }
        return total;
    }

    public List<Long> sumSize(Node n, long sizeRoot) {
        List<Long> total = new ArrayList<>();
        for(Node node : n.children.values()) {
            if(node.size>=sizeRoot-(70000000-30000000) && !node.children.isEmpty()) {
                total.add(node.size);
            }
            total.addAll(sumSize(node, sizeRoot));
        }
        return total;
    }
}

