package edu.washington.example.serialization;

import java.io.Serializable;

/**
 * A serializable cube.
 *
 * @author Russ Moul
 */
public final class Cube implements Serializable {
    /** Version Id */
    static final long serialVersionUID = 8233351143186842863L;

    /** The width. */
    private double width;

    /** The height. */
    private double height;

    /** The depth. */
    private double depth;

    /**
     * Constructor.
     *
     * @param w the cube's width
     * @param h the cube's height
     * @param d the cube's height
     */
    public Cube(final double w, final double h, final double d) {
        this.width = w;
        this.height = h;
        this.depth = d;
    }

    /**
     * Get the width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Get the depth.
     *
     * @return the depth
     */
    public double getDepth() {
        return depth;
    }

    /**
     * Returns a string represnetaion of the Cube object.
     *
     * @return a String representation of this object
     */
    public String toString() {
        return ("Height: " + height
            + "\nWidth:  " + width
            + "\nDepth:  " + depth);
    }
}
