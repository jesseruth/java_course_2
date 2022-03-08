package edu.uw.cp520.scg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for the Address class.
 */
public final class AddressSolutionTest {

    /** Test street address. */
    private static final String STREET_NUMBER = "1024 Kilobyte Dr.";

    /** Test city. */
    private static final String CITY = "Silicone Gulch";

    /** Test zip code. */
    private static final String ZIP_CODE = "94105";

    /** The object under test. */
    private Address address;

    /**
     * Set up the test fixture.
     */
    @BeforeEach
    public void setUp() {
        address = new Address(STREET_NUMBER, CITY, StateCode.CA, ZIP_CODE);
    }

    /**
     * Tear down the test fixture.
     */
    @AfterEach
    public void tearDown() {
        address = null;
    }

    /**
     * Test the getters.
     */
    @Test
    public void testAccessors() {
        assertEquals(STREET_NUMBER, address.getStreetNumber());
        assertEquals(CITY, address.getCity());
        assertEquals(StateCode.CA, address.getState());
        assertEquals(ZIP_CODE, address.getPostalCode());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        final String testString = String.format(
            "1024 Kilobyte Dr.%nSilicone Gulch, CA 94105"
        );
        assertEquals(
            testString.substring(0, 5),
            address.toString().substring(0, 5),
            "print() failed"
        );
    }
}
