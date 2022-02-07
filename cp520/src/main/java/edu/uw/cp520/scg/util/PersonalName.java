package edu.uw.cp520.scg.util;

import java.util.Objects;

/**
 * Encapsulates the first, middle and last name of a person.
 *
 * @author Jesse Ruth
 */
public final class PersonalName implements Comparable<PersonalName> {
    /**
     * String constant for NLN - No Last Name
     **/
    private static final int HASH_FACTOR = 37;
    /**
     * String constant for NLN - No Last Name
     **/
    private static final String NLN = "NLN";
    /**
     * String constant for NFN - No First Name
     **/
    private static final String NFN = "NFN";
    /**
     * String constant for NMN - No Middle Name
     **/
    private static final String NMN = "NMN";
    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Creates a new instance of Name
     */
    public PersonalName() {
        this(NFN, NMN, NLN);
    }

    /**
     * Construct a PersonalName.
     *
     * @param firstName  value for the first name.
     * @param middleName value for the middle name.
     * @param lastName   value for the last name.
     */
    public PersonalName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Construct a PersonalName.
     *
     * @param firstName value for the first name.
     * @param lastName  value for the last name.
     */
    public PersonalName(String firstName, String lastName) {
        this.firstName = firstName;
        this.middleName = NMN;
        this.lastName = lastName;
    }

    /**
     * Create string representation of this object in the format
     *
     * @return "LastName, FirstName MiddleName".
     */
    @Override
    public String toString() {
        return (lastName + ", " + firstName + " " + middleName).trim();
    }

    /**
     * Compare names for equivalence.
     *
     * @param o the name to compare to
     * @return true if all the name elements have the same value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalName)) return false;
        PersonalName that = (PersonalName) o;
        return firstName.equals(that.firstName) && Objects.equals(middleName, that.middleName) && lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }

    /**
     * Getter for firstName property.
     *
     * @return value of firstName property.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName property.
     *
     * @param firstName new value of firstName property
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for middleName property.
     *
     * @return value of middleName property.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Setter for middleName property.
     *
     * @param middleName new value of middleName property
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Getter for lastName property.
     *
     * @return value of lastName property.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName property.
     *
     * @param lastName new value of lastName property
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Compares this object with the specified object for order. Ordering by last name, first name
     * and finally middle name. Returns a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     *
     * @param personalName the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal
     * to, or greater than the specified object.
     */
    @Override
    public int compareTo(PersonalName personalName) {
        if (!this.lastName.equals(personalName.getLastName())) {
            return this.lastName.compareTo(personalName.getLastName());
        } else if (!this.firstName.equals(personalName.getFirstName())) {
            return this.firstName.compareTo(personalName.getFirstName());

        }
        return this.getMiddleName().compareTo(personalName.getMiddleName());
    }
}
