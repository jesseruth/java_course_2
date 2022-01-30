package edu.uw.cp520.scg.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFooterTest {

    @Test
    void incrementPageNumber() {
        InvoiceFooter invoiceFooter = new InvoiceFooter("Jim's Chop Shop");
        assertEquals(1, invoiceFooter.pageNumber);
        invoiceFooter.incrementPageNumber();
        invoiceFooter.incrementPageNumber();
        invoiceFooter.incrementPageNumber();
        invoiceFooter.incrementPageNumber();
        assertEquals(5, invoiceFooter.pageNumber);
    }

    @Test
    void testToString() {
        InvoiceFooter invoiceFooter = new InvoiceFooter("Jim's Chop Shop");
        String expected = "InvoiceFooter1\n" +
                "===============================================================================";
        assertNotEquals(expected, invoiceFooter.toString());
    }
}