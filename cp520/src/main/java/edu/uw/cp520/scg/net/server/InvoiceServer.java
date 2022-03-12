package edu.uw.cp520.scg.net.server;


import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.net.cmd.AbstractCommand;
import edu.uw.cp520.scg.net.cmd.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

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
    private final List<ClientAccount> clientList;
    private final List<Consultant> consultantList;
    private final String outputDirectoryName;

    /**
     * Construct an InvoiceServer with a port.
     *
     * @param port                Construct an InvoiceServer with a port.
     * @param clientList          the initial list of clients
     * @param consultantList      the initial list of consultants
     * @param outputDirectoryName the directory to be used for files output by commands
     */
    public InvoiceServer(final int port,
                         final List<ClientAccount> clientList,
                         final List<Consultant> consultantList,
                         final String outputDirectoryName) {
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
    }

    /**
     * Run this server, establishing connections, receiving commands, and dispatching them to the CommandProcesser.
     */
    public void run() {
        try (ServerSocket servSock = new ServerSocket(port);) {

            log.info("Server ready on port {}", port);

            while (!servSock.isClosed()) {
                log.info("Waiting for new connection");
                Socket sock = servSock.accept(); // blocks
                log.info("New connection, processing socket");
                process(sock);
            }
        } catch (IOException ex) {
            log.error("Server error: ", ex);
        }
    }

    private void process(final Socket sock) {
        log.info("process a socket");
        try (InputStream inStrm = sock.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(inStrm)) {

            while (!sock.isClosed()) {
                Command command = (Command) ois.readObject();
                log.info("Command Class decoded {}", command);
                CommandProcessor commandProcessor = new CommandProcessor(sock, clientList, consultantList, this);
                commandProcessor.setOutPutDirectoryName(this.outputDirectoryName);
                command.setReceiver(commandProcessor);
                command.execute();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
