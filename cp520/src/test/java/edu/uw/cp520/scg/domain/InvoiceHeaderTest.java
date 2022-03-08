package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceHeaderTest {

    private static final String BUSINESS_NAME = " Some Business";
    private static final Address BUSINESS_ADDRESS = new Address(
        "123 Main",
        "Seattle",
        StateCode.WA,
        "1228"
    );
    private static final ClientAccount BUSINESS_CLIENT = new ClientAccount(
        "Some Client",
        new PersonalName("Jim", "Jones"),
        BUSINESS_ADDRESS
    );
    private static final LocalDate INVOICE_DATE = LocalDate.of(2010, 10, 5);
    private static final LocalDate INVOICE_DATE_FOR_MONTH = LocalDate.of(2010, 10, 5);
    private InvoiceHeader invoiceHeader;

    @Test
    void testToString() {
        String expected =
            "InvoiceHeader{businessName=' Some Business', businessAddress=123 Main\n" +
            "Seattle,WA 1228, clientAccount=ClientAccount{name='Some Client', contact=Jones, Jim NMN, address=123 Main\n" +
            "Seattle,WA 1228}, invoiceDate=2010-10-05, invoiceForMonth=2010-10-05}";

        assertNotEquals(expected, invoiceHeader.toString());
    }

    @BeforeEach
    void setUp() {
        invoiceHeader =
            new InvoiceHeader(
                BUSINESS_NAME,
                BUSINESS_ADDRESS,
                BUSINESS_CLIENT,
                INVOICE_DATE,
                INVOICE_DATE_FOR_MONTH
            );
    }

    @AfterEach
    void tearDown() {
        invoiceHeader = null;
    }
}
