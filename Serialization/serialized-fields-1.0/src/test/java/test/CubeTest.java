package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import edu.washington.example.serialization.Cube;

/**
 * JUnit test case for the serializable cube class Cube.
 *
 * @author Russ Moul
 */
public final class CubeTest {
    private static final double WIDTH = 2.0;
    private static final double HEIGHT = 1.0;
    private static final double DEPTH = 3.0;

    /**
     * Tests the serialization methods.
     */
    @Test
    public void testSerialization() throws Exception {
        File serFile = new File("target/cube.ser");

        Cube kube;
        if (!serFile.exists()) {
            System.out.println("File 'cube.ser' does NOT exist, create it.");
            kube = new Cube(WIDTH, HEIGHT, DEPTH);

            // open the output file and layer an ObjectOutputStream on it
            try (FileOutputStream f = new FileOutputStream(serFile);
                 ObjectOutputStream fout = new ObjectOutputStream(f);) {

                // write the Cube object to file
                fout.writeObject(kube);
            }
        } else {
            System.out.println("File 'cube.ser' exists, just read it.");
        }

        // open the input file and layer an ObjectInputStream on it
        try (FileInputStream fis = new FileInputStream(serFile);
             ObjectInputStream fin = new ObjectInputStream(fis);) {

            // read the cube
            Cube c = (Cube) fin.readObject();
            assertEquals(WIDTH, c.getWidth(), 0.0);
            assertEquals(HEIGHT, c.getHeight(), 0.0);
            assertEquals(DEPTH, c.getDepth(), 0.0);
        }
    }

}
