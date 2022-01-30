package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class InvoiceTest {
    private final Address address = new Address("125 main", "Seattle", StateCode.WA, "12349");
    private Invoice invoice;
    private Invoice invoice2;
    private ClientAccount clientAccount;
    private static final int INVOICE_YEAR = 2020;
    private static final Month INVOICE_MONTH = Month.DECEMBER;


    @BeforeEach
    void setUp() {
        clientAccount = new ClientAccount("Some Account", new PersonalName("Jesse", "Ruth"), address);
        invoice = new Invoice(clientAccount, INVOICE_MONTH, INVOICE_YEAR);
        invoice2 = new Invoice(clientAccount, Month.JANUARY, INVOICE_YEAR);
    }

    @AfterEach
    void tearDown() {
        invoice = null;
        invoice2 = null;
        clientAccount = null;
    }

    @Test
    void getStartDate() {
        LocalDate startDate = invoice.getStartDate();
        String expectedDate = "2020-12-01";
        assertEquals(expectedDate, startDate.toString());

        startDate = invoice2.getStartDate();
        expectedDate = "2020-01-01";
        assertEquals(expectedDate, startDate.toString());
    }
    @Test
    void getGetString() {
        assertNotEquals("HELLO", invoice.toReportString());
    }
}