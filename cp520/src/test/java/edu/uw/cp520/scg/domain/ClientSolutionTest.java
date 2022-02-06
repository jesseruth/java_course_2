package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;

/**
 * JUnit test for Client class.
 */
public final class ClientSolutionTest {
    /** String constant for "FooBar Enterprises". */
    private static final String FOOBAR_ENTR = "FooBar Enterprises";
    /** String constant for "Client". */
    private static final String CLIENT = "Client";
    /** String constant for "J.". */
    private static final String J_DOT = "J.";
    /** String constant for "Random". */
    private static final String RANDOM = "Random";
    /** String constant for street address. */
    private static final String STREET = "1024 Kilobyte Dr.";
    /** String constant for city. */
    private static final String CITY = "Silicone Gulch";
    /** String constant for ZIP code. */
    private static final String ZIP = "94105";
        
    /** ClientAccount for test. */
    private ClientAccount client;

    /**
     * Perform test setup.
     */
    @BeforeEach
    public void setUp() {
        client = new edu.uw.cp520.scg.domain.ClientAccount(FOOBAR_ENTR, new PersonalName(
                CLIENT, J_DOT, RANDOM), new Address(STREET,
                        CITY, StateCode.CA, ZIP));
    }

    /**
     * Perform test tear down.
     */
    @AfterEach
    public void tearDown() {
        client = null;
    }

    /**
     * Test getName and setName methods.
     */
    @Test
    public void testSetGetName() {
        assertEquals(FOOBAR_ENTR, client.getName());
    }

    /**
     * Test getContact and setContact methods.
     */
    @Test
    public void testSetGetContact() {
        final PersonalName[] tests = {new PersonalName(), null};

        for (int i = 0; i < tests.length; i++) {
            client.setContact(tests[i]);
            assertEquals(tests[i], client.getContact());
        }
    }
}
