package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountTest {
    String accountName = "My Account";
    PersonalName contact = new PersonalName("Jesse", "Stephen");
    @Test
    void getName() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact);
        assertEquals(accountName, clientAccount.getName());
        assertEquals(contact.getFirstName(), clientAccount.getContact().getFirstName());
    }

    @Test
    void setContact() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact);
        PersonalName otherContact = new PersonalName("Joe", "Smith");
        clientAccount.setContact(otherContact);
        assertEquals(otherContact.getFirstName(), clientAccount.getContact().getFirstName());
    }

    @Test
    void isBillable() {
        ClientAccount clientAccount = new ClientAccount(accountName, contact);
        assertTrue(clientAccount.isBillable());
    }
}