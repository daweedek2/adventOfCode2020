package dk.cngroup;

import java.io.IOException;

/**
 * Created by dkostka on 12/18/2022.
 */
public interface Day {
    default Object partOne(Object puzzle) throws IOException {
        return "";
    }

    default Object partTwo(Object puzzle) throws IOException {
        return "";
    }
}
