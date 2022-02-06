package edu.uw.cp520.scg.domain;

import java.util.Locale;

/**
 * Creates an invoice footer with a business name.
 *
 * @author Jesse Ruth
 */
public class InvoiceFooter {
    /**
     * Bars to display in footer
     **/
    private static final String BARS = "===============================================================================";
    /**
     * Name of business
     **/
    private final String businessName;
    /**
     *
     */
    protected int pageNumber = 1;

    /**
     * Creates a business footer that can increment pages.
     *
     * @param businessName Name of business to print.
     */
    InvoiceFooter(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Increase the page number by 1.
     */
    public void incrementPageNumber() {
        pageNumber++;
    }

    /**
     * Prints the footer with business name and page number.
     *
     * @return A footer.
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "%n%n%n%-69s Page: %3d%n%s%n", businessName, pageNumber, BARS);
    }
}
