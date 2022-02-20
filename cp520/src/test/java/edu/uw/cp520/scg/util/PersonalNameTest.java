package edu.uw.cp520.scg.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonalNameTest {

  String firstName = "Jesse";
  String middleName = "Stephen";
  String lastName = "Ruth";

  @Test
  void testToString() {
    // An empty object will return and empty string
    PersonalName personalName = new PersonalName();
    String actualString = personalName.toString();
    String expectedString = "NLN, NFN NMN";
    assertEquals(expectedString, actualString);

    // Update First and Last Name
    personalName.setFirstName(firstName);
    personalName.setLastName(lastName);
    expectedString = "Ruth, Jesse NMN";
    actualString = personalName.toString();
    assertEquals(expectedString, actualString);

    // Add middle Name
    personalName.setMiddleName(middleName);
    expectedString = "Ruth, Jesse Stephen";
    actualString = personalName.toString();
    assertEquals(expectedString, actualString);
  }

  @Test
  void testEquals() {
    // It is equal to itself
    PersonalName personalName = new PersonalName(firstName, middleName, lastName);
    assertTrue(personalName.equals(personalName));

    // Not equal to a STRING
    String notIt = "notIt";
    assertFalse(personalName.equals(notIt));

    // Not equal
    PersonalName personalName2 = new PersonalName(firstName, lastName);
    assertFalse(personalName2.equals(personalName));
    assertFalse(personalName.equals(personalName2));

    // Update middle name
    personalName2.setMiddleName(middleName);
    assertTrue(personalName2.equals(personalName));
    assertTrue(personalName.equals(personalName2));

    PersonalName personalName3 = new PersonalName();
    assertFalse(personalName3.equals(personalName));
    personalName3.setFirstName(firstName);
    assertFalse(personalName3.equals(personalName));
    personalName3.setMiddleName(middleName);
    assertFalse(personalName3.equals(personalName));
    personalName3.setLastName(lastName);
    assertTrue(personalName3.equals(personalName));
  }

  @Test
  void testHashCode() {
    // It is equal to itself
    PersonalName personalName = new PersonalName(firstName, middleName, lastName);
    assertEquals(personalName.hashCode(), personalName.hashCode());

    // Not equal
    PersonalName personalName2 = new PersonalName(firstName, lastName);
    assertNotEquals(personalName2.hashCode(), personalName.hashCode());

    // Update middle name
    personalName2.setMiddleName(middleName);
    assertEquals(personalName2.hashCode(), personalName.hashCode());
  }

  @Test
  void getMemberValues() {
    PersonalName personalName = new PersonalName(firstName, middleName, lastName);
    assertEquals(firstName, personalName.getFirstName());
    assertEquals(middleName, personalName.getMiddleName());
    assertEquals(lastName, personalName.getLastName());
  }
}
