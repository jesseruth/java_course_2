package app;

import edu.uw.cp520.scg.net.server.InvoiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server application for assignment 09, create an InvoiceServer instance and starts it.
 *
 * @author Jesse Ruth
 */
public class Assignment09Server {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(Assignment08Server.class);

    /**
     * The port for the server to listen on.
     */
    public static final int DEFAULT_PORT = 10888;

    /**
     * Instantiates an InvoiceServer, initializes its account and consultant lists and starts it.
     *
     * @param args Command line parameters.
     * @throws Exception if the server raises any exceptions
     */
    public static void main(String[] args) throws Exception {
        log.info("Start Assignment08Server");
        InvoiceServer invoiceServer = new InvoiceServer(
                DEFAULT_PORT,
                Util.ACCOUNT_LIST,
                Util.CONSULTANT_LIST,
                "target/invoices"
        );
        invoiceServer.startServer();
    }
}
