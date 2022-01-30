package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;

/**
 * A billable Account that additionally has client contact information and an address.
 *
 * @author Jesse Ruth
 */
public final class ClientAccount implements Account {
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
     * @param name String with the name of the client.
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
     * Setter for contact property.
     *
     * @param contact new value for contact property.
     */
    public void setContact(PersonalName contact) {
        this.contact = contact;
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
     * Gets the address for this account.
     * @return value of address property.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for address property.
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
}
