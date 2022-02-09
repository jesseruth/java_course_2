package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 * Invoice encapsulates the attributes and behavior to create client invoices for a given time
 * period from time cards. The Invoice maintains are collection of invoice line items; each
 * containing date, hours and other billing information, these constitute what is being billed for
 * with this Invoice. The invoice will limit the items billed on it to a single month and also has
 * a separate invoice date which reflects the date the invoice was generated. The invoicing
 * business' name and address are obtained from a properties file. The name of the property file
 * is specified by the PROP_FILE_NAME static member.
 *
 * @author Jesse Ruth
 */
public final class Invoice {
    private static final String PROP_FILE_NAME = "invoice.properties";
    private static final String BIZ_NAME_PROP = "business.name";
    private static final String BIZ_STREET_PROP = "business.street";
    private static final String BIZ_CITY_PROP = "business.city";
    private static final String BIZ_STATE_PROP = "business.state";
    private static final String BIZ_ZIP_PROP = "business.zip";
    private static final String BIZ_NAME;
    private static final String BIZ_STREET;
    private static final String BIZ_CITY;
    private static final String BIZ_STATE;
    private static final String BIZ_ZIP;
    private static final Address BIZ_ADDRESS;
    private static final String NA = "NA";
    private static final DecimalFormat DECIMAL_DISPLAY = new DecimalFormat("#,###.00");
    private static final String TOTAL = "%nTotal: %60s  %10s%n";
    private static final String BODY_HEADER = "%-10s  %-28s %-19s  %-6s %s%n";
    private static final String LINE_ITEM = "%-10s  %-28s %-19s  %5s  %10s%n";
    private static final String BODY_HEADER_LABEL = String.format(BODY_HEADER, "Date", "Consultant", "Skill", "Hours", "Charge");
    private static final String BODY_HEADER_COLS = String.format(BODY_HEADER, "----------", "---------------------------", "------------------", "-----", "----------");
    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(Invoice.class);

    static {
        Properties prop = new Properties();
        try (InputStream in = Invoice.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME)) {
            prop.load(in);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        log.debug("Business Name Property {}", prop.get(BIZ_NAME_PROP));
        BIZ_NAME = prop.getProperty(BIZ_NAME_PROP, NA);
        BIZ_STREET = prop.getProperty(BIZ_STREET_PROP, NA);
        BIZ_CITY = prop.getProperty(BIZ_CITY_PROP, NA);
        BIZ_STATE = prop.getProperty(BIZ_STATE_PROP, NA);
        BIZ_ZIP = prop.getProperty(BIZ_ZIP_PROP, NA);
        BIZ_ADDRESS = new Address(BIZ_STREET, BIZ_CITY, StateCode.valueOf(BIZ_STATE), BIZ_ZIP);
    }

    /**
     * Client for this Invoice.
     **/
    private final ClientAccount client;
    /**
     * Month for which this Invoice is being created.
     **/
    private final Month invoiceMonth;
    /**
     * Year for which this Invoice is being created.
     **/
    private final int invoiceYear;
    /**
     * Line items for this invoice.
     */
    private final ArrayList<InvoiceLineItem> lineItems = new ArrayList<>();
    /**
     * Start date for this invoice.
     */
    private final LocalDate startDate;

    /**
     * Construct an Invoice for a client. The time period is set from the beginning to the end
     * of the month specified.
     *
     * @param client       Client for this Invoice.
     * @param invoiceMonth Month for which this Invoice is being created.
     * @param invoiceYear  Year for which this Invoice is being created.
     */
    public Invoice(final ClientAccount client, final Month invoiceMonth, final int invoiceYear) {
        this.client = client;
        this.invoiceMonth = invoiceMonth;
        this.invoiceYear = invoiceYear;
        this.startDate = LocalDate.of(invoiceYear, invoiceMonth, 1);

        log.debug("Construct an invoice for {}", client.getName());
        log.debug("Beginning {}", startDate);
    }

    /**
     * Get the start date for this Invoice, this is the earliest date a ConsultantTime instance
     * may have and still be billed on this invoice.
     *
     * @return Start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Get the invoice month.
     *
     * @return the invoice month number.
     */
    public Month getInvoiceMonth() {
        return invoiceMonth;
    }

    /**
     * Get the client for this Invoice.
     *
     * @return the client.
     */
    public ClientAccount getClientAccount() {
        return client;
    }

    /**
     * Get the total hours for this Invoice.
     *
     * @return Total hours.
     */
    public int getTotalHours() {
        log.debug("Calling get total hours");
        return lineItems.stream()
                .filter(invoiceLineItem -> invoiceLineItem.getDate().getYear() == invoiceYear && invoiceLineItem.getDate().getMonth() == getInvoiceMonth())
                .map(InvoiceLineItem::getHours)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Get the total charges for this Invoice.
     *
     * @return Total charges.
     */
    public int getTotalCharges() {
        log.debug("Calling get total charges");
        return lineItems.stream()
                .filter(invoiceLineItem -> invoiceLineItem.getDate().getYear() == invoiceYear && invoiceLineItem.getDate().getMonth() == getInvoiceMonth())
                .map(InvoiceLineItem::getCharge)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Add an invoice line item to this Invoice.
     *
     * @param lineItem InvoiceLineItem to add.
     */
    public void addLineItem(final InvoiceLineItem lineItem) {
        log.debug("Adding hours {}", lineItem.getHours());
        lineItems.add(lineItem);
    }

    /**
     * Extract the billable hours for this Invoice's client from the input TimeCard and add them to
     * the collection of line items. Only those hours for the client and month unique to this
     * invoice will be added.
     *
     * @param timeCard the TimeCard potentially containing line items for this Invoices client.
     */
    public void extractLineItems(final TimeCard timeCard) {
        log.debug("Extract line items {}", timeCard.toString());
        final List<ConsultantTime> billableHours = timeCard.getBillableHoursForClient(client.getName());
        billableHours.stream()
                .peek(consultantTime -> {
                    log.debug("Peak consultantTime {}", consultantTime);
                    log.debug("Is billable: {}", consultantTime.isBillable());
                })
                .filter(consultantTime -> consultantTime.getDate().getYear() == invoiceYear)
                .filter(consultantTime -> consultantTime.getDate().getMonth() == invoiceMonth)
                .forEach(consultantTime -> {
                    log.debug("Adding Skill: {}", consultantTime.getSkillType());
                    addLineItem(new InvoiceLineItem(consultantTime.getDate(), timeCard.getConsultant(), consultantTime.getSkillType(), consultantTime.getHours()));
                });
    }

    /**
     * Create a string representation of this object, suitable for printing.
     *
     * @return string containing this invoices client name and billing start date
     */
    @Override
    public String toString() {
        return "Invoice{" + "client=" + client + ", invoiceMonth=" + invoiceMonth + ", invoiceYear=" + invoiceYear + '}';
    }

    /**
     * Create a formatted string containing the printable invoice. Includes a header and
     * footer on each page.
     *
     * @return The formatted invoice as a string.
     */
    public String toReportString() {
        log.debug("Calling toReportString");
        final InvoiceHeader invoiceHeader = new InvoiceHeader(BIZ_NAME, BIZ_ADDRESS, client, LocalDate.now(), startDate);
        final InvoiceFooter invoiceFooter = new InvoiceFooter(BIZ_NAME);
        final int totalRows = lineItems.size() + 1; // for total
        final int pages = totalRows <= 5 ? 1 : (lineItems.size() / 5) + 1;

        log.debug("Total Pages {}", pages);

        final Iterator<InvoiceLineItem> lines = lineItems.iterator();
        final StringBuilder report = new StringBuilder();

        IntStream.rangeClosed(1, pages).forEach(page -> {
            report.append(invoiceHeader);
            report.append(BODY_HEADER_LABEL);
            report.append(BODY_HEADER_COLS);
            int counter = 1;

            while (counter <= 5 && lines.hasNext()) {
                final InvoiceLineItem invoiceLineItem = lines.next();
                final String lineCharge = DECIMAL_DISPLAY.format((double) invoiceLineItem.getCharge());
                report.append(String.format(LINE_ITEM, invoiceLineItem.getDate(), invoiceLineItem.getConsultant(), invoiceLineItem.getSkill(), invoiceLineItem.getHours(), lineCharge));
                counter++;
            }

            if (page == pages) {
                final String totalCharges = DECIMAL_DISPLAY.format((double) getTotalCharges());
                report.append(String.format(TOTAL, getTotalHours(), totalCharges));
            }

            report.append(invoiceFooter);
            invoiceFooter.incrementPageNumber();
        });

        return report.toString();
    }
}
