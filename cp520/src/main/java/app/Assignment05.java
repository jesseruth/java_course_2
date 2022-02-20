package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.util.TimeCardListUtil;
import edu.uw.ext.util.ListFactory;
import java.io.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read serialized data and print timecards and invoices.
 *
 * @author Jesse Ruth
 */
public class Assignment05 {

  /**
   * Character encoding to use.
   */
  private static final String ENCODING = "ISO-8859-1";

  /**
   * This class' logger.
   */
  private static final Logger log = LoggerFactory.getLogger(Assignment05.class);

  /**
   * Prevent instantiation.
   */
  private Assignment05() {}

  /**
   * The application method.
   *
   * @param args Command line arguments.
   * @throws Exception if raised
   */
  public static void main(final String[] args) throws Exception {
    // Create lists to be populated by factory
    @SuppressWarnings("unchecked")
    final List<ClientAccount> accounts = (List<ClientAccount>) Util.readObject(
      InitLists.CLIENTS_SER
    );
    @SuppressWarnings("unchecked")
    final List<Consultant> consultants = (List<Consultant>) Util.readObject(
      InitLists.CONSULTANTS_SER
    );
    @SuppressWarnings("unchecked")
    final List<TimeCard> timeCards = (List<TimeCard>) Util.readObject(
      InitLists.TIME_CARDS_SER
    );

    // Print them
    ListFactory.printTimeCards(timeCards);

    // Use the list util methods
    Console console = System.console();
    try {
      @SuppressWarnings("resource") // don't want to close console or System.out
      PrintWriter consoleWrtr = (console != null)
        ? console.writer()
        : new PrintWriter(new OutputStreamWriter(System.out, ENCODING), true);

      Consultant carl = consultants.get(0);
      final List<TimeCard> selected = TimeCardListUtil.getTimeCardsForConsultant(
        timeCards,
        carl
      );
      final int count = selected.size();
      consoleWrtr.printf("Counted %d time cards for %s%n", count, carl);
      if (count != 2) {
        log.error(String.format("Bad time card count for %s", carl));
      }

      TimeCardListUtil.sortByStartDate(timeCards);
      consoleWrtr.println("Time cards by date:");
      for (TimeCard tc : timeCards) {
        consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
      }

      TimeCardListUtil.sortByConsultantName(timeCards);
      consoleWrtr.println("Time cards by consultant:");
      for (TimeCard tc : timeCards) {
        consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
      }

      assert accounts != null;
      accounts.clear();
      consultants.clear();
      timeCards.clear();

      ListFactory.populateLists(accounts, consultants, timeCards);

      // Create the Invoices
      final List<Invoice> invoices = ListFactory.createInvoices(accounts, timeCards);
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

      // Now print it to a file
      try (PrintWriter fileWriter = new PrintWriter("invoices.txt", ENCODING)) {
        ListFactory.printInvoices(invoices, fileWriter);
      } catch (final IOException ex) {
        log.error("Unable to print invoices to file.", ex);
      }
    } catch (UnsupportedEncodingException e) {
      log.error("Printing of invoices failed.", e);
    }
  }
}
