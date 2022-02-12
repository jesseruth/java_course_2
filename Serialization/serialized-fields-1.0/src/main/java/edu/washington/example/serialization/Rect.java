package edu.washington.example.serialization;

/**
 * A simple rectangle class.
 *
 * @author Russ Moul
 */
public final class Rect {
    /** The width. */
    private double mWidth;

    /** The height. */
    private double mHeight;

    /**
     * Constructor.
     *
     * @param width the cube's width
     * @param height the cube's height
     */
     public Rect(final double width, final double height) {
        mHeight = height;
        mWidth = width;
    }

    /**
     * Get the width.
     *
     * @return the width
     */
    public double getWidth() {
        return mWidth;
    }

    /**
     * Get the height.
     *
     * @return the height
     */
    public double getHeight() {
        return mHeight;
    }
}
