package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;

/**
 * A consultant for the SCG, just has a PersonalName.
 *
 * @author Jesse Ruth
 */
public class Consultant implements Comparable<Consultant> {

    /**
     * Hold value of personal Name
     **/
    private final PersonalName name;

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
     * @param consultant the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final Consultant consultant) {
        return this.getName().compareTo(consultant.getName());
    }
}
