package edu.uw.cp520.scg.net.server;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.net.cmd.*;
import edu.uw.ext.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The command processor for the invoice server. Implements the receiver role in the Command design pattern, provides
 * the execute method for all the supported commands. Is provided with the client and consultant lists from the
 * Invoice server, maintains its own time card list.
 *
 * @author Jesse Ruth
 */
public final class CommandProcessor {

    /**
     * Character encoding to use.
     */
    private static final String ENCODING = "ISO-8859-1";
    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);
    private final Socket connection;
    private final List<ClientAccount> clientList;
    private final List<Consultant> consultantList;
    private static final List<TimeCard> timeCardList = new ArrayList<>();
    private final InvoiceServer server;
    private String outPutDirectoryName;

    /**
     * Construct a CommandProcessor to run in a networked environment.
     *
     * @param connection     the Socket connecting the server to the client.
     * @param clientList     the ClientList to add Clients to.
     * @param consultantList the ClientList to add Clients to.
     * @param server         the server that created this command processor
     */
    public CommandProcessor(final Socket connection,
                            final List<ClientAccount> clientList,
                            final List<Consultant> consultantList,
                            final InvoiceServer server) {
        log.info("Create new command processor");
        this.connection = connection;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.server = server;
    }

    /**
     * Set the output directory name.
     *
     * @param outPutDirectoryName the output directory name.
     */
    public void setOutPutDirectoryName(String outPutDirectoryName) {
        log.debug("Update setOutPutDirectoryName {}", outPutDirectoryName);

        this.outPutDirectoryName = outPutDirectoryName;
    }

    /**
     * Execute and AddTimeCardCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddTimeCardCommand command) {
        log.info("Execute AddTimeCardCommand");
        log.info("Total timecards: {}", timeCardList.size());
        timeCardList.add(command.getTarget());
        log.info("New timecards: {}", timeCardList.size());
    }

    /**
     * Execute an AddClientCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddClientCommand command) {
        log.info("Execute AddTimeCardCommand");
        log.info("Total clients: {}", clientList.size());
        clientList.add(command.getTarget());
        log.info("New clients: {}", clientList.size());
    }

    /**
     * Execute and AddConsultantCommand.
     *
     * @param command the command to execute.
     */
    public void execute(AddConsultantCommand command) {
        log.info("Execute AddConsultantCommand");
        log.info("Total consultants: {}", consultantList.size());
        consultantList.add(command.getTarget());
        log.info("New consultants: {}", consultantList.size());
    }

    /**
     * Execute a CreateInvoicesCommand.
     *
     * @param command the command to execute.
     */
    public void execute(CreateInvoicesCommand command) {
        LocalDate invoiceDate = command.getTarget();
        log.info("Execute CreateInvoicesCommand");

        log.info("Running Assignment 07");

        List<Invoice> invoices = new ArrayList<>(clientList.size());

        log.info("Looping through clients to make invoices");

        for (ClientAccount clientAccount : clientList) {
            log.info("Client: {}", clientAccount.getName());

            Invoice invoice = new Invoice(clientAccount, invoiceDate.getMonth(), invoiceDate.getYear());
            invoices.add(invoice);
            log.info("Added invoice: {}", invoice);
        }
        // Use the list util methods
        Console console = System.console();
        PrintWriter consoleWrtr = null;
        try {
            consoleWrtr = (console != null)
                    ? console.writer()
                    : new PrintWriter(new OutputStreamWriter(System.out, ENCODING), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

    /**
     * Execute a DisconnectCommand.
     *
     * @param command the command to execute.
     */
    public void execute(DisconnectCommand command) {
        log.info("Execute DisconnectCommand");
        try {
            command.getReceiver().connection.close();
        } catch (IOException e) {
            log.error("Disconnect Command Failed", e);
            e.printStackTrace();
        }
    }

    /**
     * Execute a ShutdownCommand.
     *
     * @param command the input ShutdownCommand.
     */
    public void execute(ShutdownCommand command) {
        log.info("Execute ShutdownCommand");
    }

}
