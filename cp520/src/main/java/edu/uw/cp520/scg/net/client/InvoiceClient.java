package edu.uw.cp520.scg.net.client;



import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.net.cmd.*;
import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * The client of the InvoiceServer. Connects to the server, sends commands to add clients, consultants and time cards
 * and then has the server create an invoice.
 *
 * @author Jesse Ruth
 */
public final class InvoiceClient {
    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(InvoiceClient.class);
    /** The socket. */
    private Socket socket;
    private final String host;
    private final  List<TimeCard> timeCardList;
    private final int port;
    /**
     * Construct an InvoiceClient with a host and port for the server.
     *
     * @param host         the host for the server.
     * @param port         the port for the server.
     * @param timeCardList the list of timeCards to send to the server
     */
    public InvoiceClient(String host, int port, List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    /**
     * Runs this InvoiceClient, sending clients, consultants, and time cards to the server, then sending the command
     * to create invoices for a specified month.
     */
    public void run() {
        log.info("Running Invoice Client");
        try {
            socket = new Socket(host, port);
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            this.sendTimeCards(oos);
            this.sendConsultants(oos);
            this.sendClients(oos);
            this.createInvoices(oos, Month.MARCH, 2017);
            this.sendDisconnect(oos, socket);

//            sendShutdown(host, port);
              os.close();
//            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Send some new clients to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendClients(ObjectOutputStream out) {
        log.info("sendClients");
        ClientAccount account = new ClientAccount("Account", new PersonalName("jim", "Bib"), new Address("125 main", "seattle", StateCode.WA, "22323"));
        AddClientCommand addClientCommand = new AddClientCommand(account);
        try {
            out.writeObject(addClientCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send some new consultants to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendConsultants(ObjectOutputStream out) {
        log.info("sendConsultants");
        Consultant consultant = new Consultant(new PersonalName("jim", "bob"));
        AddConsultantCommand addConsultantCommand = new AddConsultantCommand(consultant);
        try {
            out.writeObject(addConsultantCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the time cards to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendTimeCards(ObjectOutputStream out) {
        log.info("sendTimeCards");
        int count = 1;
        for (TimeCard timeCard: timeCardList) {
            log.info("Add timecard {} {}", count, timeCard.toString());
            AddTimeCardCommand addTimeCardCommand = new AddTimeCardCommand(timeCard);
            try {
                out.writeObject(addTimeCardCommand);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * Send the disconnect command to the server.
     *
     * @param out    the output stream connecting this client to the server.
     * @param server the connection to be closed after sending disconnect
     */
    public void sendDisconnect(ObjectOutputStream out, Socket server) {
        log.info("sendDisconnect");
        DisconnectCommand disconnectCommand = new DisconnectCommand();
        try {
            out.writeObject(disconnectCommand);
            server.close();
        } catch (IOException e) {
            log.error("Disconnect Command failed", e);
            e.printStackTrace();
        }
    }

    /**
     * Send the command to the server to create invoices for the specified month.
     *
     * @param out   the output stream connecting this client to the server.
     * @param month the month to create invoice for
     * @param year  the month to create invoice for
     */
    public void createInvoices(ObjectOutputStream out, Month month, int year) {
        log.info("createInvoices");
        CreateInvoicesCommand createInvoicesCommand = new CreateInvoicesCommand(LocalDate.of(year, month, 1));
        try {
            out.writeObject(createInvoicesCommand);
        } catch (IOException e) {
            log.error("createInvoices Command failed", e);
            e.printStackTrace();
        }

    }

    /**
     * Send the shutdown command to the server, this is done on a separate connection to the server.
     *
     * @param host the host for the server.
     * @param port the host for the server.
     */
    public static void sendShutdown(String host, int port) {
        log.info("sendShutdown");

    }

}
