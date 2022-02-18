package app;

import java.io.*;

public class Util {
    /**
     * Serializes the object to the filename.
     *
     * @param fileName Filename to write
     * @param object   Object to write
     */
    public static void writeObject(final String fileName, Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileName)
            );
            out.writeObject(object);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Gets a list of objects from a file.
     *
     * @param fileName filename to load.
     * @return A list oj objects from the file.
     */
    public static Object readObject(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(fileName)
            );
            return in.readObject();

        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
