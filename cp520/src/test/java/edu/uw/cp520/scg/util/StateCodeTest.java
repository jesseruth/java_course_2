package edu.uw.cp520.scg.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateCodeTest {

    @Test
    void testToString() {
        String actual = StateCode.AS.toString();
        String expected = "AS";
        assertEquals(expected, actual);
    }

    @Test
    void testGetName() {
        String actual = StateCode.AS.getName();
        String expected = "AMERICAN SAMOA";
        assertEquals(expected, actual);
    }

    @Test
    void testForName() {
        String nameLookup = "AMERICAN SAMOA";
        StateCode actual = StateCode.WA.forName(nameLookup);
        assertEquals(StateCode.AS, actual);

        nameLookup = "DISNEY";
        actual = StateCode.WA.forName(nameLookup);
        assertNull(actual);
    }
}