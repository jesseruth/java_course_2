package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientAccountTest {
    final String accountName = "My Account";
    final PersonalName contact = new PersonalName("Jesse", "Stephen");
    final Address address = new Address("125 main", "Seattle", StateCode.WA, "12349");

    @Test
    void getName() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact, address);
        assertEquals(accountName, clientAccount.getName());
        assertEquals(contact.getFirstName(), clientAccount.getContact().getFirstName());
    }

    @Test
    void setContact() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact, address);
        PersonalName otherContact = new PersonalName("Joe", "Smith");
        clientAccount.setContact(otherContact);
        assertEquals(otherContact.getFirstName(), clientAccount.getContact().getFirstName());
    }

    @Test
    void isBillable() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact, address);
        assertTrue(clientAccount.isBillable());
    }
}