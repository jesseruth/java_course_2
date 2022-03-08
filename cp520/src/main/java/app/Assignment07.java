package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.persistent.DbServer;
import edu.uw.ext.util.ListFactory;
import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates and prints an invoice from the database.
 *
 * @author Jesse Ruth
 */
public final class Assignment07 {

    /**
     * Character encoding to use.
     */
    private static final String ENCODING = "ISO-8859-1";
    /**
     * Report Month
     */
    private static final Month MONTH = Month.MARCH;
    /**
     * Report Year
     */
    private static final int YEAR = 2017;
    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(Assignment07.class);
    /**
     * DB Server
     */
    private static final DbServer dbServer;

    static {
        dbServer = GetDbServer.getServer();
    }

    /**
     * Connects to the database and create an invoice to the test month and year and prints it.
     *
     * @param args command arguments - not used.
     * @throws Exception If anything goes wrong.
     */
    public static void main(String[] args) throws Exception {
        log.info("Running Assignment 07");
        List<ClientAccount> clients = dbServer.getClients();
        List<Invoice> invoices = new ArrayList<>(clients.size());
        for (Consultant consultant : dbServer.getConsultants()) {
            log.info("Found Consultant: {}", consultant);
        }
        log.info("Looping through clients to make invoices");

        for (ClientAccount clientAccount : clients) {
            log.info("Client: {}", clientAccount.getName());

            Invoice invoice = dbServer.getInvoice(clientAccount, MONTH, YEAR);
            invoices.add(invoice);
            log.info("Added invoice: {}", invoice.toString());
        }
        // Use the list util methods
        Console console = System.console();
        PrintWriter consoleWrtr = (console != null)
            ? console.writer()
            : new PrintWriter(new OutputStreamWriter(System.out, ENCODING), true);

        // Create the Invoices
        // Print them
        consoleWrtr.println();
        consoleWrtr.println(
            "=================================================================================="
        );
        consoleWrtr.println(
            "=============================== I N V O I C E S =================================="
        );
        consoleWrtr.println(
            "=================================================================================="
        );
        consoleWrtr.println();
        ListFactory.printInvoices(invoices, consoleWrtr);
    }
}
