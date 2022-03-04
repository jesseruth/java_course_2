package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.persistent.DbServer;
import edu.uw.ext.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
  private static final String PROP_FILE_NAME = "database.properties";
  private static final String DB_URL_PROP = "url";
  private static final String DB_USERNAME_PROP = "username";
  private static final String DB_PASSWORD_PROP = "password";

  private static final String DB_URL;
  private static final String DB_USERNAME;
  private static final String DB_PASSWORD;
  private static final String NA = "NA";
  private static final Month MONTH = Month.MARCH;
  private static final int YEAR = 2017;
  /**
   * Da Logger
   */
  private static final Logger log = LoggerFactory.getLogger(Assignment07.class);
  private static final DbServer dbServer;

  static {
    Properties prop = new Properties();
    try (
            InputStream in = Invoice.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME)
    ) {
      prop.load(in);
    } catch (final IOException e) {
      e.printStackTrace();
    }

    DB_URL = prop.getProperty(DB_URL_PROP, NA);
    DB_USERNAME = prop.getProperty(DB_USERNAME_PROP, NA);
    DB_PASSWORD = prop.getProperty(DB_PASSWORD_PROP, NA);
    dbServer = new DbServer(DB_URL, DB_USERNAME, DB_PASSWORD);
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
    clients.forEach(clientAccount -> {
      try {
        Invoice invoice = dbServer.getInvoice(clientAccount, MONTH, YEAR);
        invoices.add(invoice);
        log.info("Added invoice: {}", invoice.toString());
      } catch (SQLException e) {
        log.error("No invoice for this client", e);
        e.printStackTrace();
      }
    });
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
