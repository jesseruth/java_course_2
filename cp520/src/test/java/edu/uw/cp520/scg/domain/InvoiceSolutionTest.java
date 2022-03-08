package edu.uw.cp520.scg.domain;

import static edu.uw.ext.util.ListFactory.TEST_INVOICE_MONTH;
import static edu.uw.ext.util.ListFactory.TEST_INVOICE_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for Invoice class.
 */
public final class InvoiceSolutionTest {

    /** String constant for "FooBar Enterprises". */
    private static final String FOOBAR = "FooBar.com";
    /** String constant for "Client". */
    private static final String CLIENT = "Client";
    /** String constant for "J.". */
    private static final String J_DOT = "J.";
    /** String constant for "Random". */
    private static final String RANDOM = "Random";
    /** String constant for "Coder". */
    private static final String CODER = "Coder";
    /** String constant for "Carl". */
    private static final String CARL = "Carl";
    /** String constant for street address. */
    private static final String STREET = "1024 Kilobyte Dr.";
    /** String constant for city. */
    private static final String CITY = "Silicone Gulch";
    /** String constant for ZIP code. */
    private static final String ZIP = "94105";

    /** Number of hours in a standard working day. */
    private static final int HOURS_PER_DAY = 8;

    /** The first Monday of the test month. */
    private static final int TEST_START_FIRST_WEEK = 6;

    /** Expected total hours. */
    private static final int TOTAL_HOURS = 32;

    /** Expected total charges. */
    private static final int TOTAL_CHARGES = 4800;

    /** Invoice instance for test. */
    private Invoice invoice4Lines;
    private Invoice invoice5Lines;
    private Invoice invoice6Lines;

    /**
     * Perform test setup.
     */
    @BeforeEach
    public void setUp() {
        ClientAccount client = new ClientAccount(
            FOOBAR,
            new PersonalName(CLIENT, J_DOT, RANDOM),
            new Address(STREET, CITY, StateCode.CA, ZIP)
        );

        Consultant programmer = new Consultant(new PersonalName(CODER, CARL));

        LocalDate currentDate = LocalDate.of(
            TEST_INVOICE_YEAR,
            TEST_INVOICE_MONTH,
            TEST_START_FIRST_WEEK
        );
        TimeCard timecard = new TimeCard(programmer, currentDate);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        currentDate = currentDate.plusDays(1);

        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        currentDate = currentDate.plusDays(1);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        currentDate = currentDate.plusDays(1);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        currentDate = currentDate.plusDays(1);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                NonBillableAccount.VACATION,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        // Add a ConsultantTime that is out of the date range
        LocalDate nextMonth = currentDate.plusMonths(1);

        timecard.addConsultantTime(
            new ConsultantTime(nextMonth, client, Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY)
        );

        invoice4Lines = new Invoice(client, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
        invoice4Lines.extractLineItems(timecard);

        currentDate = currentDate.plusDays(1);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        invoice5Lines = new Invoice(client, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
        invoice5Lines.extractLineItems(timecard);

        currentDate = currentDate.plusDays(1);
        timecard.addConsultantTime(
            new ConsultantTime(
                currentDate,
                client,
                Skill.SOFTWARE_ENGINEER,
                HOURS_PER_DAY
            )
        );
        invoice6Lines = new Invoice(client, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
        invoice6Lines.extractLineItems(timecard);
    }

    /**
     * Test the getStartDate method.
     */
    @Test
    public void testGetStartDate() {
        LocalDate expectedDate = LocalDate.of(TEST_INVOICE_YEAR, TEST_INVOICE_MONTH, 1);

        assertEquals(
            expectedDate,
            invoice4Lines.getStartDate(),
            "getStartDate() failed: "
        );
    }

    /**
     * Test the getInvoiceMonth method.
     */
    @Test
    public void testGetInvoiceMonth() {
        assertEquals(
            Month.MARCH,
            invoice4Lines.getInvoiceMonth(),
            "getInvoiceMonth() failed: "
        );
    }

    /**
     * Test the getTotalHours method.
     */
    @Test
    public void testGetTotalHours() {
        assertEquals(
            TOTAL_HOURS,
            invoice4Lines.getTotalHours(),
            "getTotalHours() failed: "
        );
    }

    /**
     * Test the gettotalCharges method.
     */
    @Test
    public void testGetTotalCharges() {
        assertEquals(
            TOTAL_CHARGES,
            invoice4Lines.getTotalCharges(),
            "getTotalCharges() failed: "
        );
    }

    /**
     * Determine the last page number on the invoice.
     * @param invoiceString the invoice string
     * @return the last page number
     */
    private int getLastPageNumber(final String invoiceString) {
        int pageNum = 0;
        int ndx = invoiceString.lastIndexOf("Page:");
        if (ndx > 0) {
            String pageNumStr = invoiceString.substring(ndx + 5, ndx + 9).trim();
            pageNum = Integer.parseInt(pageNumStr);
        }
        return pageNum;
    }

    /**
     * Test paging.
     */
    @Test
    public void testPaging() {
        assertTrue(getLastPageNumber(invoice4Lines.toReportString()) == 1);
        // Is the summary suppose to be on a new page?
        assertTrue(getLastPageNumber(invoice5Lines.toReportString()) == 2);
        assertTrue(getLastPageNumber(invoice6Lines.toReportString()) == 2);
    }
}
