package edu.uw.cp520.scg.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressTest {

    private static final String streetNumber = "1234 Main St.";
    private static final String city = "Seattle";
    private static final StateCode state = StateCode.WA;
    private static final String postalCode = "123345";
    private static final List<Address> addresses = new ArrayList<>();
    private static Address address;
    private static Address address2;

    @BeforeAll
    static void build() {
        addresses.add(new Address(null, city, state, postalCode));
        addresses.add(new Address(streetNumber, null, state, postalCode));
        addresses.add(new Address(streetNumber, city, null, postalCode));
        addresses.add(new Address(streetNumber, city, state, null));
        addresses.add(new Address("Nope", city, state, postalCode));
        addresses.add(new Address(streetNumber, "Nope", state, postalCode));
        addresses.add(new Address(streetNumber, city, StateCode.AK, postalCode));
        addresses.add(new Address(streetNumber, city, state, "Nope"));
    }

    @BeforeEach
    void setUp() {
        address = new Address(streetNumber, city, state, postalCode);
        address2 = new Address(streetNumber, city, state, postalCode);
    }

    @AfterEach
    void tearDown() {
        address = null;
    }

    @Test
    void testToString() {
        final String actual = address.toString();
        final String expected = "1234 Main St.\n" + "Seattle, WA 123345";
        assertEquals(expected, actual);
    }

    @Test
    void testCompareTo() {
        // expect them to be equal
        int actual = address.compareTo(address2);
        assertEquals(0, actual);

        Address otherAddress = new Address(streetNumber, city, state, "Nope");
        actual = address.compareTo(otherAddress);
        assertTrue(actual < 0);
        actual = otherAddress.compareTo(address);
        assertTrue(actual > 0);

        otherAddress = new Address(streetNumber, city, StateCode.AK, postalCode);
        actual = address.compareTo(otherAddress);
        assertTrue(actual > 0);
        actual = otherAddress.compareTo(address);
        assertTrue(actual < 0);

        otherAddress = new Address(streetNumber, "NOPE", state, postalCode);
        actual = address.compareTo(otherAddress);
        assertTrue(actual > 0);
        actual = otherAddress.compareTo(address);
        assertTrue(actual < 0);

        otherAddress = new Address("NOPE", city, state, postalCode);
        actual = address.compareTo(otherAddress);
        assertTrue(actual < 0);
        actual = otherAddress.compareTo(address);
        assertTrue(actual > 0);
    }

    @Test
    void testEquals() {
        // Equal to itself
        assertEquals(address, address);
        // Equal to another address with same values
        assertEquals(address, address2);
        // Not equal to another object
        assertFalse(address.equals("Some String"));
        // Not equal to a string
        assertNotEquals(null, address);
        // Test all scenarios
        addresses
            .stream()
            .forEach(address1 -> {
                assertNotEquals(address, address1);
                assertNotEquals(address1, address);
            });

        assertEquals(
            (new Address(null, city, state, postalCode)),
            new Address(null, city, state, postalCode)
        );
        assertEquals(
            (new Address(streetNumber, null, state, postalCode)),
            new Address(streetNumber, null, state, postalCode)
        );
        assertEquals(
            (new Address(streetNumber, city, null, postalCode)),
            new Address(streetNumber, city, null, postalCode)
        );
        assertEquals(
            (new Address(streetNumber, city, state, null)),
            new Address(streetNumber, city, state, null)
        );
    }

    @Test
    void testHashcode() {
        addresses
            .stream()
            .forEach(address1 -> {
                assertNotEquals(address.hashCode(), address1.hashCode());
                assertNotEquals(address1.hashCode(), address.hashCode());
            });
    }
}
