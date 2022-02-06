package edu.uw.cp520.scg.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testToString() {
        String streetNumber = "1234 Main St.";
        String city = "Seattle";
        StateCode state = StateCode.WA;
        String postalCode = "123345";
        Address address = new Address(streetNumber, city, state, postalCode);
        String actual = address.toString();
        String expected = "1234 Main St.\n" +
                "Seattle, WA 123345";
        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        String streetNumber = "1234 Main St.";
        String city = "Seattle";
        StateCode state = StateCode.WA;
        String postalCode = "123345";
        Address address1 = new Address(streetNumber, city, state, postalCode);
        Address address2 = new Address(streetNumber, city, state, postalCode);

        // Equal to itself
        assertTrue(address1.equals(address1));
        // Equal to another address with same values
        assertTrue(address1.equals(address2));
        // Not equal to another object
        assertFalse(address1.equals("Some String"));
        // Not equal to a string
        assertFalse(address1.equals(null));
        // Test all scenarios
        assertFalse(address1.equals(new Address(null, city, state, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, null, state, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, city, null, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, city, state, null)));
        assertFalse(address1.equals(new Address("Nope", city, state, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, "Nope", state, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, city, StateCode.AK, postalCode)));
        assertFalse(address1.equals(new Address(streetNumber, city, state, "Nope")));
        // Test all scenarios
        assertFalse((new Address(null, city, state, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, null, state, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, city, null, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, city, state, null)).equals(address1));
        assertFalse((new Address("Nope", city, state, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, "Nope", state, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, city, StateCode.AK, postalCode)).equals(address1));
        assertFalse((new Address(streetNumber, city, state, "Nope")).equals(address1));

        assertTrue((new Address(null, city, state, postalCode)).equals(new Address(null, city, state, postalCode)));
        assertTrue((new Address(streetNumber, null, state, postalCode)).equals(new Address(streetNumber, null, state, postalCode)));
        assertTrue((new Address(streetNumber, city, null, postalCode)).equals(new Address(streetNumber, city, null, postalCode)));
        assertTrue((new Address(streetNumber, city, state, null)).equals(new Address(streetNumber, city, state, null)));

    }
}