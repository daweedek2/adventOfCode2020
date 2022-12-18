package dk.cngroup;/*
 * Copyright (c) 1995, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import com.google.common.base.Objects;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

/**
 * A point representing a location in {@code (x,y)} coordinate space,
 * specified in integer precision.
 *
 * @author Sami Shaio
 * @since 1.0
 */
public class Loc implements Serializable {
    public final long x;
    public final long y;

    public Loc() {
        this(0, 0);
    }

    public Loc(Loc p) {
        this(p.x, p.y);
    }

    public Loc(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Loc(Point p) {
        this(p.x, p.y);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Loc move(int dx, int dy) {
        return new Loc(x + dx, y + dy);
    }

    public Loc move(Loc l) {
        return new Loc(x + l.x, y + l.y);
    }

    public Loc move(long dx, long dy) {
        return new Loc(x + dx, y + dy);
    }

    public Point getPoint() {
        return new Point(intX(), intY());
    }

    public static Stream<Loc> range(int i, int j){
        return IntStream.range(0, i).boxed().flatMap(x -> IntStream.range(0, j).mapToObj(y -> new Loc(x, y)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        if (x != loc.x) return false;
        return y == loc.y;
    }

    @Override
    public int hashCode() {
        int result = (int) (x ^ (x >>> 32));
        result = 31 * result + (int) (y ^ (y >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }

    public int intX() {
        return toIntExact(x);
    }

    public int intY() {
        return toIntExact(y);
    }

    public long distance(Loc pt) {
        return Math.abs(pt.x-x) + Math.abs(pt.y-y);
    }

    public Stream<Loc> eightDirs() {
        return Arrays.stream(Direction.eightDirections()).map(d -> d.move(this));
    }

    public Stream<Loc> expand(long howMuch) {
        return new Range(new Loc(x-(howMuch/2), y-(howMuch/2)), new Loc(x+(howMuch/2), y+(howMuch/2))).stream();
    }

    public Stream<Loc> walk(Direction dir, long howFar) {
        return IntStream.range(0, toIntExact(howFar)).mapToObj(i -> dir.move(this, i));
    }
}