package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A consultant for the SCG, just has a PersonalName.
 *
 * @author Jesse Ruth
 */
public class Consultant implements Comparable<Consultant>, Serializable {

  private static final long serialVersionUID = -7761113182555737926L;

  /**
   * This class' logger.
   */
  private static final Logger log = LoggerFactory.getLogger(Consultant.class);
  private static final ObjectStreamField[] serialPersistentFields = {
    new ObjectStreamField("name", PersonalName.class),
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
    return Comparator.comparing(Consultant::getName).compare(this, other);
  }

  /**
   * Writes object proxy to stream.
   *
   * @return Serialization proxy.
   */
  private Object writeReplace() {
    return new SerializationProxy(this);
  }

  /**
   * Throws InvalidObjectException
   *
   * @param ois not used
   * @throws InvalidObjectException always
   */
  private void readObject(final ObjectInputStream ois) throws InvalidObjectException {
    throw new InvalidObjectException("Must use proxy");
  }

  private static final class SerializationProxy implements Serializable {

    private static final long serialVersionUID = 5813422724954601551L;

    /**
     * last name
     **/
    private final String x;

    /**
     * first name
     **/
    private final String y;

    /**
     * middle name
     **/
    private final String z;

    SerializationProxy(final Consultant consultant) {
      final String msg = String.format(
        "Serializing consultant: %s",
        consultant.getName()
      );
      log.info(msg);
      final PersonalName name = consultant.getName();
      x = name.getLastName();
      y = name.getFirstName();
      z = name.getMiddleName();
    }

    private Object readResolve() {
      final String msg = String.format("De-serialized consultant: %s %s %S", z, y, z);
      log.info(msg);
      return new Consultant(new PersonalName(y, z, x));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Consultant)) return false;

    Consultant that = (Consultant) o;

    return getName().equals(that.getName());
  }
}
