package edu.washington.example.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

/**
 * Cube class that uses field serialization, width and height fields are mapped
 * to a Rect object.
 *
 * @author Russ Moul
 */
public final class Cube implements Serializable {
    /** Version Id */
    static final long serialVersionUID = 8233351143186842863L;

    /** The serialization fields */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("width", double.class),
        new ObjectStreamField("height", double.class),
        new ObjectStreamField("depth", double.class)
    };

    /** Primary dimensions of the cube. */
    private Rect mRect;

    /** The depth. */
    private double mDepth;

    /**
     * Constructor.
     *
     * @param width the cube's width
     * @param height the cube's height
     * @param depth the cube's height
     */
    public Cube(final double width, final double height,
                     final double depth) {
        mRect = new Rect(width, height);
        mDepth = depth;
    }

    /**
     * Get the width.
     *
     * @return the width
     */
    public double getWidth() {
        return mRect.getWidth();
    }

    /**
     * Get the height.
     *
     * @return the height
     */
    public double getHeight() {
        return mRect.getHeight();
    }

    /**
     * Get the depth.
     *
     * @return the depth
     */
     public double getDepth() {
        return mDepth;
    }

    /**
     * Reads the object fields from stream.
     *
     * @param ois the stream to read the object from
     *
     * @throws IOException if any I/O exceptions occur
     * @throws ClassNotFoundException if the read object's class can't be loaded
     */
    private void readObject(final ObjectInputStream ois)
        throws ClassNotFoundException, IOException {
        System.out.println("Reading into new version");

        // Read version one types
        ObjectInputStream.GetField fields = ois.readFields();
        double w = fields.get("width", 0.0);
        double h = fields.get("height", 0.0);
        mDepth = fields.get("depth", 0.0);
        mRect = new Rect(w, h);
    }

    /**
     * Writes the object fields to stream.
     *
     * @param oos the stream to write the object to
     *
     * @throws IOException if any I/O exceptions occur
     */
     private void writeObject(final ObjectOutputStream oos) throws IOException {
        System.out.println("Writing from new version");

        // Convert to version one types
        ObjectOutputStream.PutField fields = oos.putFields();
        fields.put("width", mRect.getWidth());
        fields.put("height", mRect.getHeight());
        fields.put("depth", mDepth);
        oos.writeFields();
    }

    /**
     * Returns a string represnetaion of the Cube object.
     *
     * @return a String representation of this object
     */
    public String toString() {
        return ("Height: " + mRect.getHeight()
            + "\nWidth:  " + mRect.getWidth()
            + "\nDepth:  " + mDepth);
    }
}
