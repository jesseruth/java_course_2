package edu.uw.cp520.scg.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        AccountTestClass accountTestClass = new AccountTestClass();
        assertEquals("Jesse Stephen Ruth", accountTestClass.getName());
    }

    @Test
    void testIsBillable() {
        AccountTestClass accountTestClass = new AccountTestClass();
        assertEquals(false, accountTestClass.isBillable());
    }


    private class AccountTestClass implements Account {

        @Override
        public String getName() {
            return "Jesse Stephen Ruth";
        }

        @Override
        public boolean isBillable() {
            return false;
        }
    }

}