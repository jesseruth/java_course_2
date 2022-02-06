package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;

/**
 * JUnit test for TimeCard class.
 */
public final class TimeCardSolutionTest {
    /** The test year. */
    private static final int TEST_YEAR = 2004;
    /** The test start day. */
    private static final int START_DAY = 6;
    /** String constant for "Acme Industries'. */
    private static final String ACME = "Acme Industries";
    /** String constant for "Contact'. */
    private static final String CONTACT = "Contact";
    /** String constant for "Guy'. */
    private static final String GUY = "Guy";
    /** Street address. */
    private static final String STREET = "1616 Index Ct.";
    /** String constant for "XX'. */
    private static final String CITY = "Redmond";
    /** Address ZIP code. */
    private static final String ZIP = "98055";
    /** String constant for "Programmer'. */
    private static final String PROGRAMMER = "Programmer";
    /** String constant for "J.'. */
    private static final String J_DOT = "J.";
    /** String constant for "Random'. */
    private static final String RANDOM = "Random";
    /** Constant for work hours per day. */
    private static final int WORK_DAY_HOURS = 8;
    /** Consulting hours. */
    private static final int CONSULTING_HOURS = 2;
    /** Tital hours. */
    private static final int TOTAL_HOURS = 16;

    
    /** TimeCard instance for test. */
    private TimeCard timecard;
    /** ClientAccount instance for test. */
    private ClientAccount client;
    /** Test start date. */
    private final LocalDate date = LocalDate.of(TEST_YEAR, Month.JANUARY, START_DAY);

    /**
     * Perform test setup.
     */
    @BeforeEach
    public void setUp() {
        client = new ClientAccount(ACME,
                new PersonalName(CONTACT, GUY), new Address(STREET,
                        CITY, StateCode.WA, ZIP));
        final NonBillableAccount nonbillableaccount = NonBillableAccount.VACATION;
        timecard = new TimeCard(new Consultant(new PersonalName(PROGRAMMER, J_DOT, RANDOM)), date);
        ConsultantTime consultantTime = new ConsultantTime(date, client,
                Skill.SYSTEM_ARCHITECT, WORK_DAY_HOURS);
        timecard.addConsultantTime(consultantTime);
        consultantTime = new ConsultantTime(date.plusDays(1), nonbillableaccount,
                Skill.SYSTEM_ARCHITECT, WORK_DAY_HOURS);
        timecard.addConsultantTime(consultantTime);
    }

    /**
     * Test the constructors.
     */
    public void testConstructor() {
        assertNotNull(timecard, "TimeCard constructor failed");
        assertNotNull(timecard.getConsultant(),
                      "TimeCard constructor didn't set consultant");
        assertNotNull(timecard.getConsultingHours(),
                      "TimeCard constructor didn't set consultingHours");
        assertNotNull(timecard.getWeekStartingDay(),
                      "TimeCard constructor didn't set weekStartingDay");
        assertEquals(0, timecard.getTotalBillableHours(),
                     "TimeCard constructor didn't set weekStartingDay");
        assertEquals(0, timecard.getTotalNonBillableHours(),
                     "TimeCard constructor didn't set totalNonBillableHours");
        assertEquals(0, timecard.getTotalHours(), Double.MIN_VALUE,
                     "TimeCard constructor didn't set totalHours");
    }

    /**
     * Tests the getConsultant method.
     */
    @Test
    public void testGetConsultant() {
        assertNotNull(timecard.getConsultant(), "getConsultant() failed");
    }

    /**
     * Tests the getTotalBillableHours method.
     */
    @Test
    public void testGetTotalBillableHours() {
        assertEquals(WORK_DAY_HOURS, timecard.getTotalBillableHours());
    }

    /**
     * Tests the getTotalNonBillableHours method.
     */
    @Test
    public void testGetTotalNonBillableHours() {
        assertEquals(WORK_DAY_HOURS, timecard.getTotalNonBillableHours());
    }

    /**
     * Tests the getConsultingHours method.
     */
    @Test
    public void testGetConsultingHours() {
        assertEquals(CONSULTING_HOURS, timecard.getConsultingHours().size());
    }

    /**
     * Tests the getTotalHours method.
     */
    @Test
    public void testGetTotalHours() {
        assertEquals(TOTAL_HOURS, timecard.getTotalHours());
    }

    /**
     * Tests the getBillableHoursForClient method.
     */
    @Test
    public void testGetBillableHoursForClient() {
        assertEquals(1, timecard.getBillableHoursForClient(client.getName()).size());
    }
}
