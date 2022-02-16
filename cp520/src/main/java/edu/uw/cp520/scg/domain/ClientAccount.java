package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A billable Account that additionally has client contact information and an address.
 *
 * @author Jesse Ruth
 */
public final class ClientAccount implements Account, Comparable<ClientAccount>, Serializable {
    private static final long serialVersionUID = 5813422724954601551L;

    /**
     * Holds value of property name.
     */
    private final String name;

    /**
     * Holds value of property contact.
     */
    private PersonalName contact;

    /**
     * Holds value of property contact.
     */
    private Address address;

    /**
     * Creates a new instance of ClientAccount.
     *
     * @param name    String with the name of the client.
     * @param contact Name of the contact person for this client.
     * @param address Addres of this client.
     */
    public ClientAccount(String name, PersonalName contact, Address address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    /**
     * Gets the account name.
     *
     * @return value of name property.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the contact for this account.
     *
     * @return value of contact property.
     */
    public PersonalName getContact() {
        return contact;
    }

    /**
     * Setter for contact property.
     *
     * @param contact new value for contact property.
     */
    public void setContact(PersonalName contact) {
        this.contact = contact;
    }

    /**
     * Gets the address for this account.
     *
     * @return value of address property.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for address property.
     *
     * @param address new value for address property
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Determines if this account is billable.
     *
     * @return Always true
     */
    @Override
    public boolean isBillable() {
        return true;
    }

    @Override
    public String toString() {
        return "ClientAccount{" +
                "name='" + name + '\'' +
                ", contact=" + contact +
                ", address=" + address +
                '}';
    }

    /**
     * The natural ordering of ClientAccount is ascending order by name, contact and finally address.
     *
     * @param other the Client to be compared.
     * @return a negative integer, zero, or a positive integer as this Client is less than,
     * equal to, or greater than the specified Client.
     */
    @Override
    public int compareTo(ClientAccount other) {
        return Comparator.comparing(ClientAccount::getName)
                .thenComparing(ClientAccount::getContact)
                .thenComparing(ClientAccount::getAddress)
                .compare(this, other);
    }
}
