package app;

import edu.uw.cp520.scg.net.client.InvoiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The client application for Assignment08, create an InvoiceClient instance and use it to send the various commands to the server, and disconnects, and then on a separate connection sends the shutdown command.
 *
 * @author Jesse Ruth
 */
public class Assignment08 {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(Assignment08.class);
    /**
     * Default local host
     */
    private static final String LOCAL_HOST = "127.0.0.1";

    /**
     * Instantiates an InvoiceClient, provides it with a set of timecards to send the server and starts it running,
     * and then send the shutdown command.
     *
     * @param args Command line parameters, not used
     * @throws Exception on any errors.
     */
    public static void main(String[] args) throws Exception {
        log.info("Running Assignment08 Client");
        InvoiceClient invoiceClient = new InvoiceClient(
            LOCAL_HOST,
            Assignment08Server.DEFAULT_PORT,
            Util.TIME_CARD_LIST
        );
        invoiceClient.run();
    }
}
