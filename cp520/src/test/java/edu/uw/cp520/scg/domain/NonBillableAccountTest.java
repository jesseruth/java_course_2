package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import org.junit.jupiter.api.Test;

class NonBillableAccountTest {

    @Test
    void compareTo() {
        NonBillableAccount sick = NonBillableAccount.SICK_LEAVE;
        NonBillableAccount vacation = NonBillableAccount.VACATION;
        NonBillableAccount bizDev = NonBillableAccount.BUSINESS_DEVELOPMENT;

        NonBillableAccount[] values = NonBillableAccount.values();

        assertEquals(values[0], sick);
        assertEquals("Vacation", vacation.getName());
        assertEquals("Business development", values[2].toString());
        assertFalse(bizDev.isBillable());
    }

    @Test
    void isBillable() {
        NonBillableAccount[] values = NonBillableAccount.values();
        for (NonBillableAccount account : values) {
            assertFalse(account.isBillable());
        }
    }
}
