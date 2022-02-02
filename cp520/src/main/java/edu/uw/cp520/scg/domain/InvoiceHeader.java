package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * This class prints an invoice header.
 *
 * @author Jesse Ruth
 */
public final class InvoiceHeader {
    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(InvoiceHeader.class);
    /**
     * Business Name
     **/
    private final String businessName;
    /**
     * Address for business.
     **/
    private final Address businessAddress;
    /**
     * Account for invoice.
     **/
    private final ClientAccount clientAccount;
    /**
     * Date invoice created
     **/
    private final LocalDate invoiceDate;
    /**
     * Month invoice covers
     **/
    private final LocalDate invoiceForMonth;

    /**
     * Create an invoice header.
     *
     * @param businessName    The business name.
     * @param businessAddress The business address.
     * @param clientAccount   The client account.
     * @param invoiceDate     The invoice date.
     * @param invoiceForMonth The invoice month.
     */
    public InvoiceHeader(String businessName, Address businessAddress, ClientAccount clientAccount, LocalDate invoiceDate, LocalDate invoiceForMonth) {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.clientAccount = clientAccount;
        this.invoiceDate = invoiceDate;
        this.invoiceForMonth = invoiceForMonth;
    }

    /**
     * Prints and invoice header with Business info and dates;
     *
     * @return An invoice header.
     */
    @Override
    public String toString() {
        log.debug("Creating an invoice header for {}", businessName);
        StringBuilder header = new StringBuilder();
        header.append(String.format("%s%n", businessName));
        header.append(String.format("%s%n%n", businessAddress));
        header.append(String.format("Invoice for:%n%s%n%s%n%n", clientAccount.getName(), clientAccount.getAddress()));
        header.append(String.format("Invoice For Month of: %1$tB %1$tY%n", invoiceForMonth));
        header.append(String.format("Invoice Date: %1$tB %1$td, %1$tY%n%n", invoiceDate));
        return header.toString();
    }
}
