package edu.uw.cp520.scg.util;

import java.util.Objects;

/**
 * Encapsulates the first, middle and last name of a person.
 *
 * @author Jesse Ruth
 */
public final class PersonalName {
    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Create string representation of this object in the format
     *
     * @return "LastName, FirstName MiddleName".
     */
    @Override
    public String toString() {
        if (lastName.isEmpty() && firstName.isEmpty() && middleName.isEmpty()) return "";
        return  (lastName + ", " + firstName + " " + middleName).trim() ;
    }

    /**
     * Creates a new instance of Name
     */
    public PersonalName() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
    }

    /**
     * Construct a PersonalName.
     * @param firstName value for the first name.
     * @param middleName value for the middle name.
     * @param lastName value for the last name.
     */
    public PersonalName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Construct a PersonalName.
     * @param firstName value for the first name.
     * @param lastName value for the last name.
     */
    public PersonalName(String firstName, String lastName) {
        this.firstName = firstName;
        this.middleName = "";
        this.lastName = lastName;
    }

    /**
     * Compare names for equivalence.
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
     * @return value of firstName property.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for firstName property.
     * @param firstName new value of firstName property
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Getter for middleName property.
     * @return value of middleName property.
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * Setter for middleName property.
     * @param middleName new value of middleName property
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * Getter for lastName property.
     * @return value of lastName property.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for lastName property.
     * @param lastName new value of lastName property
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
