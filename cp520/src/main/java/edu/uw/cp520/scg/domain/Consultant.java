package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Comparator;

/**
 * A consultant for the SCG, just has a PersonalName.
 *
 * @author Jesse Ruth
 */
public class Consultant implements Comparable<Consultant>, Serializable {

    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(Consultant.class);


    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("name", PersonalName.class)
    };

    /**
     * Hold value of personal Name
     **/
    private PersonalName name;

    /**
     * Creates a new instance of Consultant.
     *
     * @param name the consultant's name.
     */
    public Consultant(PersonalName name) {
        this.name = name;
    }

    /**
     * Getter for name property.
     *
     * @return value of name property.
     */
    public final PersonalName getName() {
        return name;
    }

    /**
     * Returns the string representation of the consultant's name.
     *
     * @return the consultant name string
     */
    @Override
    public String toString() {
        return name.toString();
    }

    /**
     * The Consultant natural ordering is in ascending ordered by the consultant's name.
     *
     * @param other the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final Consultant other) {
        return Comparator.comparing(Consultant::getName)
                .compare(this, other);
    }

    /**
     * Reads the object fields from stream.
     *
     * @param ois the stream to read the object from
     * @throws ClassNotFoundException if the read object's class can't be loaded
     * @throws IOException            if any I/O exceptions occur
     */
    private void readObject(final ObjectInputStream ois) throws ClassNotFoundException, IOException {
        log.info("Reading into new version");
        ObjectInputStream.GetField fields = ois.readFields();
        var n = fields.get("name", null);
        if (n != null) {
            name = (PersonalName) n;
        }
    }

    /**
     * Writes the object fields to stream.
     *
     * @param oos the stream to write the object to
     * @throws IOException if any I/O exceptions occur
     */
    private void writeObject(final ObjectOutputStream oos) throws IOException {
        log.info("Writing from new version");
        ObjectOutputStream.PutField fields = oos.putFields();
        fields.put("name", getName());
        oos.writeFields();
    }
}
