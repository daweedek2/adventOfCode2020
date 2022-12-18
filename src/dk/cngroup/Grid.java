package dk.cngroup;

/**
 * Created by dkostka on 12/4/2021.
 */
import java.util.stream.IntStream;

public interface Grid {
    default int countChar(char... c) {
        return Math.toIntExact(iterate().filter(e -> new String(c).chars().anyMatch(i -> i == e)).count());
    }

    IntStream iterate();
}
