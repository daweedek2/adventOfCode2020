package dk.cngroup.year2022;

import dk.cngroup.AbstractDay;
import dk.cngroup.HexDirection;
import dk.cngroup.Loc3D;
import dk.cngroup.Utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dkostka on 12/4/2022.
 */
public class Day18 extends AbstractDay {

    public static void main(String args[]) throws IOException {
        Day18 day = new Day18();
        day.solve();
    }

    @Override
    public String getDay() {
        return "18";
    }

    @Override
    public Object getPuzzle() throws IOException {
        return Utils.getFileLines(getDay());
    }

    @Override
    public Object partOne(final Object puzzle) throws IOException {
        List<Loc3D> locs = Utils.getFileLines(getDay()).map(s -> Utils.readString(s, "%n,%n,%n", Loc3D.class)).toList();
        long connecting = locs.stream()
                .flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1)))
                .filter(locs::contains)
                .count();
        return (locs.size() * 6L) - connecting;
    }


    @Override
    public Object partTwo(final Object puzzle) throws IOException {
        List<Loc3D> locs = Utils.getFileLines(getDay()).map(s -> Utils.readString(s, "%n,%n,%n", Loc3D.class)).toList();
        List<Loc3D> connecting = locs.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).collect(Collectors.toCollection(ArrayList::new));
        List<Loc3D> exterior = connecting.stream().filter(locs::contains).toList();
        connecting.removeAll(exterior);
        List<Set<Loc3D>> pockets = connecting.stream().map(l -> new HashSet<>(Set.of(l))).collect(Collectors.toCollection(ArrayList::new));
        int trapped = 0;
        for(int i = 0; i<1000; i++) {
            for(int j = 0; j<pockets.size(); j++) {
                Set<Loc3D> pocket = pockets.get(j);
                Set<Loc3D> s = new HashSet<>(pocket);
                pocket.addAll(pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet()));
                if(s.size() == pocket.size()) {
                    trapped++;
                    pockets.remove(j);
                    j--;
                } else if(pocket.size()>2000) {
                    pockets.remove(j);
                    j--;
                }
            }
        }
        return (locs.size()*6L) - exterior.size() - trapped;
    }
}

