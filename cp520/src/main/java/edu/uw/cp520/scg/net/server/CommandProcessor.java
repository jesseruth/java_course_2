package edu.uw.cp520.scg.net.server;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.net.cmd.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The command processor for the invoice server. Implements the receiver role in the Command design pattern, provides
 * the execute method for all the supported commands. Is provided with the client and consultant lists from the
 * Invoice server, maintains its own time card list.
 *
 * @author Jesse Ruth
 */
public final class CommandProcessor implements Runnable {

    /**
     * Character encoding to use.
     */
    private static final String ENCODING = "ISO-8859-1";

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);
    /**
     * The Socket Connection
     **/
    private final Socket connection;
    /**
     * The client list
     **/
    private final List<ClientAccount> clientList;
    /**
     * The consultant list
     **/
    private final List<Consultant> consultantList;
    /**
     * The timecard list
     **/
    private final List<TimeCard> timeCardList = new ArrayList<>();
    /**
     * The server that runs this command processor
     **/
    private final InvoiceServer server;
    /**
     * Directory to output files for this server
     **/
    private String outPutDirectoryName = ".";

    /**
     * Construct a CommandProcessor to run in a networked environment.
     *
     * @param connection     the Socket connecting the server to the client.
     * @param clientList     the ClientList to add Clients to.
     * @param consultantList the ClientList to add Clients to.
     * @param server         the server that created this command processor
     */
    public CommandProcessor(
        final Socket connection,
        final List<ClientAccount> clientList,
        final List<Consultant> consultantList,
        final InvoiceServer server
    ) {
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
    public void setOutPutDirectoryName(final String outPutDirectoryName) {
        log.debug("Update setOutPutDirectoryName {}", outPutDirectoryName);
        this.outPutDirectoryName = outPutDirectoryName;
    }

    /**
     * Execute and AddTimeCardCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final AddTimeCardCommand command) {
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
    public void execute(final AddClientCommand command) {
        log.info("Execute AddTimeCardCommand");
        log.info("Total clients: {}", clientList.size());
        synchronized (clientList) {
            if (!clientList.contains(command.getTarget())) {
                clientList.add(command.getTarget());
            }
        }
        log.info("New clients: {}", clientList.size());
    }

    /**
     * Execute and AddConsultantCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final AddConsultantCommand command) {
        log.info("Execute AddConsultantCommand");
        log.info("Total consultants: {}", consultantList.size());
        synchronized (consultantList) {
            if (!consultantList.contains(command.getTarget())) {
                consultantList.add(command.getTarget());
            }
        }
        log.info("New consultants: {}", consultantList.size());
    }

    /**
     * Execute a CreateInvoicesCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final CreateInvoicesCommand command) {
        final LocalDate invoiceDate = command.getTarget();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
            "MMMMyyyy"
        );
        final String monthString = dateTimeFormatter.format(invoiceDate);
        log.info("Execute CreateInvoicesCommand");

        log.info("Running Assignment 07");

        final List<Invoice> invoices = new ArrayList<>(clientList.size());

        log.info("Looping through clients to make invoices");

        synchronized (clientList) {
            for (ClientAccount clientAccount : clientList) {
                log.info("Client: {}", clientAccount.getName());

                Invoice invoice = new Invoice(
                    clientAccount,
                    invoiceDate.getMonth(),
                    invoiceDate.getYear()
                );
                invoices.add(invoice);
                for (final TimeCard timeCard : timeCardList) {
                    invoice.extractLineItems(timeCard);
                }
                log.info("Added invoice: {}", invoice);
                if (invoice.getTotalHours() > 0) {
                    final File serverDir = new File(outPutDirectoryName);
                    if (!serverDir.exists()) {
                        if (!serverDir.mkdirs()) {
                            log.error("Unable to create directory");
                            return;
                        }
                    }
                    final String fileName = String.format(
                        "%s%sInvoice.txt",
                        clientAccount.getName().replaceAll(" ", ""),
                        monthString
                    );
                    final File outputFile = new File(outPutDirectoryName, fileName);
                    try (
                        PrintStream printStream = new PrintStream(
                            new FileOutputStream(outputFile)
                        )
                    ) {
                        printStream.println(invoice.toReportString());
                    } catch (FileNotFoundException e) {
                        log.error("File not found", e);
                    }
                }
            }
        }
        //        if (PRINT_INVOICES = true) {
        //            // Use the list util methods
        //            Console console = System.console();
        //            PrintWriter consoleWrtr = null;
        //            try {
        //                consoleWrtr =
        //                        (console != null)
        //                                ? console.writer()
        //                                : new PrintWriter(new OutputStreamWriter(System.out, ENCODING), true);
        //            } catch (UnsupportedEncodingException e) {
        //                e.printStackTrace();
        //            }
        //
        //            // Create the Invoices
        //            // Print them
        //            consoleWrtr.println();
        //            consoleWrtr.println(
        //                    "=================================================================================="
        //            );
        //            consoleWrtr.println(
        //                    "=============================== I N V O I C E S =================================="
        //            );
        //            consoleWrtr.println(
        //                    "=================================================================================="
        //            );
        //            consoleWrtr.println();
        //            ListFactory.printInvoices(invoices, consoleWrtr);
        //        }
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
        try {
            command.getReceiver().connection.close();
        } catch (IOException e) {
            log.error("Disconnect Command Failed", e);
            e.printStackTrace();
        } finally {
            server.shutdown();
        }
    }

    /**
     * Run this CommandProcessor, reads Commands from the connection and executes them.
     */
    @Override
    public void run() {
        final String threadName = Thread.currentThread().getName();
        log.info("RUUUUUNNNNNN FOREST, RUN!!!!!! Thread: {}", threadName);
        try (
            InputStream inStrm = connection.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inStrm)
        ) {
            while (!connection.isClosed()) {
                final Object obj = ois.readObject();
                if (obj == null) {
                    connection.close();
                } else if (obj instanceof Command<?>) {
                    final Command<?> command = (Command<?>) obj;
                    log.info("Command Class decoded {}", command);
                    setOutPutDirectoryName(
                        String.format("target/invoices/%s", threadName)
                    );
                    command.setReceiver(this);
                    command.execute();
                } else {
                    log.info("Invalid command {}", obj.getClass().getSimpleName());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.error("Socket is closed! Thread: {}", Thread.currentThread().getName());
        } catch (IOException e) {
            log.error("IOException! Thread: {}", Thread.currentThread().getName(), e);
        } catch (ClassNotFoundException e) {
            log.error(
                "ClassNotFoundException! Thread: {}",
                Thread.currentThread().getName(),
                e
            );
        }
    }
}
