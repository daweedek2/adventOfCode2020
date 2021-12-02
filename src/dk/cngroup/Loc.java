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

/**
 * A point representing a location in {@code (x,y)} coordinate space,
 * specified in integer precision.
 *
 * @author Sami Shaio
 * @since 1.0
 */
public class Loc implements Serializable {
    /**
     * The X coordinate of this {@code Point}.
     * If no X coordinate is set it will default to 0.
     *
     * @serial
     * @see #move(int, int)
     * @since 1.0
     */
    public final long x;

    /**
     * The Y coordinate of this {@code Point}.
     * If no Y coordinate is set it will default to 0.
     *
     * @serial
     * @see #move(int, int)
     * @since 1.0
     */
    public final long y;

    /**
     * Use serialVersionUID from JDK 1.1 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = -5276940640259749850L;

    /**
     * Constructs and initializes a point at the origin
     * (0,&nbsp;0) of the coordinate space.
     *
     * @since 1.1
     */
    public Loc() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified {@code Point} object.
     *
     * @param p a point
     * @since 1.1
     */
    public Loc(Loc p) {
        this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified
     * {@code (x,y)} location in the coordinate space.
     *
     * @param x the X coordinate of the newly constructed {@code Point}
     * @param y the Y coordinate of the newly constructed {@code Point}
     * @since 1.0
     */
    public Loc(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Loc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Loc(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    public double getY() {
        return y;
    }

    /**
     * Translates this point, at location {@code (x,y)},
     * by {@code dx} along the {@code x} axis and {@code dy}
     * along the {@code y} axis so that it now represents the point
     * {@code (x+dx,y+dy)}.
     *
     * @param dx the distance to move this point
     *           along the X axis
     * @param dy the distance to move this point
     *           along the Y axis
     */
    public Loc move(int dx, int dy) {
        return new Loc(x + dx, y + dy);
    }

    public Loc move(Loc l) {
        return new Loc(x + l.x, y + l.y);
    }

    public Point getPoint() {
        return new Point(Math.toIntExact(x), Math.toIntExact(y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Loc loc = (Loc) o;
        return x == loc.x && y == loc.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), x, y);
    }

    /**
     * Returns a string representation of this point and its location
     * in the {@code (x,y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be {@code null}.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}