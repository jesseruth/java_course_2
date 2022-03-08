package edu.uw.cp520.scg.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A simple mailing address. Does no validity checking for things like valid states or postal codes.
 * Instances of this class are immutable.
 *
 * @author Jesse Ruth
 */
public class Address implements Comparable<Address>, Serializable {

    private static final long serialVersionUID = -3505598498871347327L;

    /**
     * The street number
     **/
    private final String streetNumber;
    /**
     * The city
     **/
    private final String city;
    /**
     * The state
     **/
    private final StateCode state;
    /**
     * The postal code
     **/
    private final String postalCode;

    /**
     * Construct an Address.
     *
     * @param streetNumber the street number.
     * @param city         the city.
     * @param state        the state.
     * @param postalCode   the postal code.
     */
    public Address(String streetNumber, String city, StateCode state, String postalCode) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    /**
     * Get the street number for this address.
     *
     * @return the street number.
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Gets the city for this address.
     *
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the state code for this address.
     *
     * @return the state code.
     */
    public StateCode getState() {
        return state;
    }

    /**
     * Gets the postal code for this address.
     *
     * @return the postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Prints this address in the form:
     * <p>
     * street number
     * city, state postal code
     *
     * @return the formatted address.
     */
    @Override
    public String toString() {
        return String.format(
            "%s\n%s, %s %s",
            getStreetNumber(),
            getCity(),
            getState(),
            getPostalCode()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (
            streetNumber != null
                ? !streetNumber.equals(address.streetNumber)
                : address.streetNumber != null
        ) return false;
        if (
            city != null ? !city.equals(address.city) : address.city != null
        ) return false;
        if (state != address.state) return false;
        return postalCode != null
            ? postalCode.equals(address.postalCode)
            : address.postalCode == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = streetNumber != null ? streetNumber.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    /**
     * Orders by state, postalCode, city and streetNumber.
     *
     * @param other an address to compare to.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final Address other) {
        return Comparator
            .comparing(Address::getState)
            .thenComparing(Address::getPostalCode)
            .thenComparing(Address::getCity)
            .thenComparing(Address::getStreetNumber)
            .compare(this, other);
    }
}
