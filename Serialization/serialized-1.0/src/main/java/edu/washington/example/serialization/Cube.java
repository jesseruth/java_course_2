package edu.washington.example.serialization;

import java.io.Serializable;


/**
 * A serializable cube.
 *
 * @author Russ Moul
 */
public final class Cube implements Comparable<Cube>, Serializable {
    /** Serial version id, */
	private static final long serialVersionUID = -4634077380137794802L;

	/** The surface area. */
    private transient double mSurfaceArea;

    /** The volume area. */
    private transient double mVolume;

    /** The height. */
    private double mHeight;

    /** The width. */
    private double mWidth;

    /** The depth. */
    private double mDepth;

    /**
     * Constructor.
     *
     * @param width the cube's width
     * @param height the cube's height
     * @param depth the cube's height
     */
    public Cube(final double width, final double height, final double depth) {
        this.mHeight = height;
        this.mWidth = width;
        this.mDepth = depth;
        mSurfaceArea = ((mWidth * mHeight)
                     + (mWidth * mDepth)
                     + (mHeight * mDepth)) * 2;
        mVolume = mWidth * mHeight * mDepth;
    }

    /**
     * Compares two Cubes, two cubes are considered equal if they have the
     * same width, height and depth.  During comparision the properties have
     * the following order of significance width, height and depth.
     *
     * @param otherCube the Cube to be compared
     *
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object
     *
     * @throws ClassCastException  if the specified object is not an Cube
     */
    public int compareTo(final Cube otherCube) throws ClassCastException {
        double diff = mWidth - otherCube.mWidth;
        if (diff == 0) {
            diff = mHeight - otherCube.mHeight;
        }
        if (diff == 0) {
            diff = mDepth - otherCube.mDepth;
        }
        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }

    /** Hash code seed. */
    private static final int HASH_SEED = 17;

    /** Hash multiplier. */
    private static final int HASH_MULTIPLIER = 37;

    /** Size of int, half size of long. */
    private static final int BITS_32 = 32;

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    public int hashCode() {
        int hc = HASH_SEED;
        long v;
        v = Double.doubleToLongBits(mWidth);
        hc = HASH_MULTIPLIER * hc + (int)(v ^ (v >>> BITS_32));
        v = Double.doubleToLongBits(mWidth);
        hc = HASH_MULTIPLIER * hc + (int)(v ^ (v >>> BITS_32));
        v = Double.doubleToLongBits(mWidth);
        hc = HASH_MULTIPLIER * hc + (int)(v ^ (v >>> BITS_32));
        return hc;
    }

    /**
     * Compares two Cubes, two cubes are considered equal if they have the
     * same width, height and depth.
     *
     * @param o the Object to be compared
     *
     * @return true if and only if the width, height and depth are equal.
     */
    public boolean equals(final Object o) {
        boolean eq = false;
        if (o != null && o instanceof Cube) {
            eq = compareTo((Cube)o) == 0;
        }
        return eq;
    }

    /**
     * Returns a string represnetaion of the Cube object.
     *
     * @return a String representation of this object
     */
    public String toString() {
        return ("Height: " + mHeight + "\nWidth:  " + mWidth
              + "\nDepth:  " + mDepth + "\nArea:   " + mSurfaceArea
              + "\nVolume: " + mVolume);
    }

    /**
     * @return the mHeight
     */
    public final double getHeight() {
        return mHeight;
    }

    /**
     * @return the mWidth
     */
    public final double getWidth() {
        return mWidth;
    }

    /**
     * @return the mDepth
     */
    public final double getDepth() {
        return mDepth;
    }

}
