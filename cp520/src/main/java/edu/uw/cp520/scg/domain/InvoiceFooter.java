package edu.uw.cp520.scg.domain;

/**
 * Creates an invoice footer with a business name.
 *
 * @author Jesse Ruth
 */
public class InvoiceFooter {
    /** Bars to display in footer **/
    private static final String BARS = "===============================================================================";
    /** Name of business **/
    private final String businessName;
    /**
     *
     */
    protected int pageNumber = 1;

    /**
     * Increase the page number by 1.
     */
    public void incrementPageNumber() {
        pageNumber++;
    }

    /**
     * Creates a business footer that can increment pages.
     *
     * @param businessName Name of business to print.
     */
    InvoiceFooter(String businessName) {
        this.businessName = businessName;
    }

    /**
     * Prints the footer with business name and page number.
     *
     * @return A footer.
     */
    @Override
    public String toString() {
        return String.format("%n%n%-70s%9s%n%s%n%n",businessName, pageNumber, BARS);
    }
}
