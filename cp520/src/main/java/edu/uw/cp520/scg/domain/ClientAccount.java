package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;

/**
 * A billable Account that additionally has client contact information.
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
     * Creates a new instance of ClientAccount.
     *
     * @param name String with the name of the client.
     * @param contact Name of the contact person for this client.
     */
    public ClientAccount(String name, PersonalName contact) {
        this.name = name;
        this.contact = contact;
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
     * Determines if this account is billable.
     *
     * @return Always true
     */
    @Override
    public boolean isBillable() {
        return true;
    }
}
