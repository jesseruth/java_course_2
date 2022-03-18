package edu.uw.cp520.scg.net.server;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.net.cmd.Command;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The server for creating new clients, consultants and time cards as well as creation of account invoices. Maintains
 * it's own list of clients and consultants, but not time cards.
 *
 * @author Jesse Ruth
 */
public class InvoiceServer {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(InvoiceServer.class);
    /**
     * The listening port.
     */
    private final int port;
    /**
     * List of clients on this server.
     */
    private final List<ClientAccount> clientList;
    /**
     * List of consultants maintained by server.
     */
    private final List<Consultant> consultantList;
    /**
     * The name of the directory to be used by output commands
     **/
    private final String outputDirectoryName;

    private ServerSocket servSock;

    /**
     * Construct an InvoiceServer with a port.
     *
     * @param port                Construct an InvoiceServer with a port.
     * @param clientList          the initial list of clients
     * @param consultantList      the initial list of consultants
     * @param outputDirectoryName the directory to be used for files output by commands
     */
    public InvoiceServer(
        final int port,
        final List<ClientAccount> clientList,
        final List<Consultant> consultantList,
        final String outputDirectoryName
    ) {
        this.port = port;
        this.clientList = Collections.synchronizedList(clientList);
        this.consultantList = Collections.synchronizedList(consultantList);
        this.outputDirectoryName = outputDirectoryName;
    }

    /**
     * Run this server, establishing connections, receiving commands, and dispatching them to the CommandProcesser.
     */
    public void startServer() {
        try (ServerSocket listenSocket = new ServerSocket(port);) {
            servSock = listenSocket;
            log.info("Server ready on port {}", port);

            while (!servSock.isClosed()) {
                log.info("Waiting for new connection");
                try {
                    log.info("New connection, processing socket");
                    Socket sock = servSock.accept();
                    process(sock);
                } catch (final SocketException s) {
                    log.error("Server socket closed", s);
                }
            }
        } catch (IOException ex) {
            log.error("Server error: ", ex);
        }
    }

    private void process(final Socket sock) {
        log.info("process a socket");
        CommandProcessor commandProcessor = new CommandProcessor(
                sock,
                clientList,
                consultantList,
                this
        );
        Thread thread = new Thread(commandProcessor);
        thread.start();
    }

    /**
     * Shutdown this server.
     */
    void shutdown() {
        try {
            if (servSock != null && !servSock.isClosed()) {
                servSock.close();
            }
        } catch (final IOException e) {
            log.error("Unable to shutdown server", e);
        }
    }
}
