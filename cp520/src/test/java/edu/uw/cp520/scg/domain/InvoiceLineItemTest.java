package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class InvoiceLineItemTest {
    @Test
    void testInvoiceLineItemHoursAndCharge() {
        LocalDate localDate = LocalDate.parse("1978-03-14");
        Consultant consultant = new Consultant(new PersonalName("Jesse", "RUth"));
        int hours = 10;
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem(localDate, consultant, Skill.PROJECT_MANAGER, hours);
        int expectedCharge = hours * Skill.PROJECT_MANAGER.getRate();
        assertEquals(hours, invoiceLineItem.getHours());
        assertEquals(expectedCharge, invoiceLineItem.getCharge());
    }

    @Test
    void testInvoiceLineItemToString() {
        LocalDate localDate = LocalDate.parse("1978-03-14");
        Consultant consultant = new Consultant(new PersonalName("Jesse", "RUth"));
        int hours = 10;
        InvoiceLineItem invoiceLineItem = new InvoiceLineItem(localDate, consultant, Skill.PROJECT_MANAGER, hours);
        String expectedString = "InvoiceLineItem{date=1978-03-14, consultant=RUth, Jesse NMN, skill=Project manager skill, hours=10}";
        assertEquals(expectedString, invoiceLineItem.toString());
    }
}