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

    /**
     * Tests the serialization.
     */
    @Test
	public void testSerialization() throws Exception {
        File serFile = new File("target/cube.ser");

        Cube kube = new Cube(2.0, 1.0, 3.0);

        // open the output file and layer an ObjectOutputStream on it
        try (FileOutputStream f = new FileOutputStream(serFile);
             ObjectOutputStream fout = new ObjectOutputStream(f);) {

            // write the Cube object to file
            fout.writeObject(kube);
        }

        // open the input file and layer an ObjectInputStream on it
        try (FileInputStream fis = new FileInputStream(serFile);
             ObjectInputStream fin = new ObjectInputStream(fis);) {

            // read the cube
            Cube c = (Cube) fin.readObject();
            assertEquals(kube, c);
        }
    }

}
