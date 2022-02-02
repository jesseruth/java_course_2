package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;

/**
 * A consultant for the SCG, just has a PersonalName.
 *
 * @author Jesse Ruth
 */
public class Consultant {

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
}
