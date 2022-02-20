package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientAccountTest {

  private final String accountName = "My Account";
  private final PersonalName contact = new PersonalName("Jesse", "Stephen");
  private final Address address = new Address(
    "125 main",
    "Seattle",
    StateCode.WA,
    "12349"
  );
  private final Address address2 = new Address(
    "125 front",
    "New York",
    StateCode.NY,
    "12349"
  );
  ClientAccount clientAccount;
  ClientAccount otherClientAccount;

  @BeforeEach
  void setup() {
    clientAccount = new ClientAccount(accountName, contact, address);
    otherClientAccount = new ClientAccount(accountName, contact, address);
  }

  @AfterEach
  void tearDown() {
    clientAccount = null;
  }

  @Test
  void getName() {
    assertEquals(accountName, clientAccount.getName());
    assertEquals(contact.getFirstName(), clientAccount.getContact().getFirstName());
  }

  @Test
  void setContact() {
    PersonalName otherContact = new PersonalName("Joe", "Smith");
    clientAccount.setContact(otherContact);
    assertEquals(otherContact.getFirstName(), clientAccount.getContact().getFirstName());
  }

  @Test
  void isBillable() {
    assertTrue(clientAccount.isBillable());
  }

  @Test
  void setAddress() {
    assertTrue(clientAccount.getAddress().equals(address));
    clientAccount.setAddress(address2);
    assertTrue(clientAccount.getAddress().equals(address2));
  }

  @Test
  void compareTo() {
    int actual = clientAccount.compareTo(otherClientAccount);
    assertEquals(0, actual);
    clientAccount.setAddress(address2);
    actual = clientAccount.compareTo(otherClientAccount);
    assertTrue(actual < 0);
    actual = otherClientAccount.compareTo(clientAccount);
    assertTrue(actual > 0);
    otherClientAccount.setAddress(address2);
    actual = otherClientAccount.compareTo(clientAccount);
    assertEquals(0, actual);
    actual =
      otherClientAccount.compareTo(
        new ClientAccount(accountName, new PersonalName("Jim", "Bob"), address)
      );
    assertTrue(actual < 0);
    actual =
      otherClientAccount.compareTo(new ClientAccount("Other Account", contact, address));
    assertTrue(actual < 0);
  }
}
