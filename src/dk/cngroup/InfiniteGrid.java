package dk.cngroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.empty;

/**
 * Created by dkostka on 12/15/2022.
 */
public class InfiniteGrid implements Grid {
    final Map<Loc, Character> grid;

    public InfiniteGrid(char[][] g) {
        this();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                grid.put(new Loc(j, i), g[i][j]);
            }
        }
    }

    public InfiniteGrid(Map<Loc, Character> grid) {
        this.grid = grid;
    }

    public InfiniteGrid() {
        this.grid = new HashMap<>();
    }

    public IntStream iterate() {
        return grid.values().stream().mapToInt(e -> e);
    }

    public Optional<Character> get(Loc p) {
        return grid.containsKey(p) ? Optional.of(grid.get(p)) : empty();
    }

    public void set(Loc p, char c) {
        grid.put(p, c);
    }

    public String toString() {
        long minX = grid.keySet().stream().mapToLong(e -> e.x).min().orElse(0);
        long minY = grid.keySet().stream().mapToLong(e -> e.y).min().orElse(0);
        long maxX = grid.keySet().stream().mapToLong(e -> e.x).max().orElse(0);
        long maxY = grid.keySet().stream().mapToLong(e -> e.y).max().orElse(0);
        CharGrid g = new CharGrid(' ', new Loc(maxX+1-minX, maxY+1-minY));
        grid.forEach((p, i) -> g.set(new Loc(p.x-minX, p.y-minY), i));
        return g.toString();
    }

    public static Collector<Loc, Map<Loc, Character>, InfiniteGrid> toInfiniteGrid(char c) {
        final Supplier<Map<Loc, Character>> supplier = HashMap::new;
        final BiConsumer<Map<Loc, Character>, Loc> accumulator = (a, b) -> a.put(b, c);
        final BinaryOperator<Map<Loc, Character>> combiner = (a, b) -> {a.putAll(b); return a;};
        final Function<Map<Loc, Character>, InfiniteGrid> finisher = a -> new InfiniteGrid(a);
        return Collector.of(supplier, accumulator, combiner, finisher);
    }

    public boolean canPlace(InfiniteGrid g, long r, long c) {
        return g.grid.keySet().stream().map(l -> l.move(c, r)).noneMatch(grid::containsKey);
    }

    public boolean canPlace(InfiniteGrid g) {
        return g.grid.keySet().stream().noneMatch(grid::containsKey);
    }

    public long minY() {
        return grid.keySet().stream().mapToLong(e -> e.y).min().orElse(0);
    }

    public long maxY() {
        return grid.keySet().stream().mapToLong(e -> e.y).max().orElse(0);
    }

    public long minX() {
        return grid.keySet().stream().mapToLong(e -> e.x).min().orElse(0);
    }

    public long maxX() {
        return grid.keySet().stream().mapToLong(e -> e.x).max().orElse(0);
    }

    public InfiniteGrid move(long x, long y) {
        return new InfiniteGrid(grid.entrySet().stream().map(e -> new Pair<>(e.getKey().move(x, y), e.getValue())).collect(Collectors.toMap(Pair::a, Pair::b)));
    }

    public void place(InfiniteGrid s) {
        s.grid.forEach(this::set);
    }

    public long height() {
        return maxY()+1-minY();
    }

    public long width() {
        return maxX()+1-minX();
    }
}
