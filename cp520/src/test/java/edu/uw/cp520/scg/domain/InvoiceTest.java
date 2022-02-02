package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceTest {
    private static final int INVOICE_YEAR = 2020;
    private static final Month INVOICE_MONTH = Month.DECEMBER;
    private static final Address ADDRESS = new Address("125 main", "Seattle", StateCode.WA, "12349");
    private static final PersonalName CONTACT = new PersonalName("Jim", "Bob");
    private static final PersonalName CONTACT_2 = new PersonalName("Mary", "Joe");
    private static final String ACCOUNT_NAME = "Account 1";
    private static final ClientAccount ACCOUNT = new ClientAccount(ACCOUNT_NAME, CONTACT, ADDRESS);
    private static final String INVOICE_EXAMPLE = "invoice.txt";
    private static final Consultant CONSULTANT = new Consultant(CONTACT);
    private static final TimeCard TIMECARD = new TimeCard(CONSULTANT, LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, 2));
    private static final Consultant CONSULTANT_2 = new Consultant(CONTACT_2);
    private Invoice invoice;
    private Invoice invoice2;

    @BeforeEach
    void setUp() {
        invoice = new Invoice(ACCOUNT, INVOICE_MONTH, INVOICE_YEAR);
        IntStream.rangeClosed(1, 11).forEach(i -> {
            invoice.addLineItem(new InvoiceLineItem(LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, i), CONSULTANT, Skill.PROJECT_MANAGER, 10));
        });
        IntStream.rangeClosed(1, 5).forEach(i -> {
            invoice.addLineItem(new InvoiceLineItem(LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, i), CONSULTANT_2, Skill.PROJECT_MANAGER, 10));
        });
        invoice.addLineItem(new InvoiceLineItem(LocalDate.of(1900, INVOICE_MONTH, 22), CONSULTANT_2, Skill.PROJECT_MANAGER, 10));
        invoice.addLineItem(new InvoiceLineItem(LocalDate.of(INVOICE_YEAR, 5, 22), CONSULTANT_2, Skill.PROJECT_MANAGER, 10));

        invoice2 = new Invoice(ACCOUNT, Month.JANUARY, INVOICE_YEAR);
        IntStream.rangeClosed(1, 4).forEach(i -> {
            invoice2.addLineItem(new InvoiceLineItem(LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, i), CONSULTANT_2, Skill.PROJECT_MANAGER, 10));
        });
    }

    @AfterEach
    void tearDown() {
        invoice = null;
        invoice2 = null;
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
    void getReportGetString() throws IOException {
        String expected = new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(INVOICE_EXAMPLE)).readAllBytes());
        assertEquals(expected, invoice.toReportString());
    }

    @Test
    void getGetString() {
        String expected = "Invoice{client=ClientAccount{name='Account 1', contact=Bob, Jim NMN, address=125 main\n" +
                "Seattle,WA 12349}, invoiceMonth=DECEMBER, invoiceYear=2020}";
        assertEquals(expected, invoice.toString());
    }

    @Test
    void testExtractLineItems() {
        TIMECARD.addConsultantTime(new ConsultantTime(LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, 2), ACCOUNT, Skill.PROJECT_MANAGER, 100));
        TIMECARD.addConsultantTime(new ConsultantTime(LocalDate.of(INVOICE_YEAR, INVOICE_MONTH, 2), NonBillableAccount.VACATION, Skill.PROJECT_MANAGER, 100));
        int chargesBefore = invoice.getTotalCharges();
        assertEquals(40000, chargesBefore);
        invoice.extractLineItems(TIMECARD);

        int chargesAfter = invoice.getTotalCharges();
        assertEquals(65000, chargesAfter);
    }
}