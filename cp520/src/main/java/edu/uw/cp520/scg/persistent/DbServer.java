package edu.uw.cp520.scg.persistent;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.domain.TimeCard;

import java.sql.SQLException;
import java.time.Month;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a programmatic interface to store and access objects in the database.
 *
 * @author Jesse Ruth
 */
public final class DbServer {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DbServer.class);

    /**
     * Constructor
     *
     * @param dbUrl    the database URL
     * @param username the database username
     * @param password the database password
     */
    public DbServer(String dbUrl, String username, String password) {
        log.info("\nURL: {} \nUsername: {}\nPassword: {}", dbUrl, username, password);

    }

    /**
     * Add a client to the database, inserts one row in the clients table.
     *
     * @param client the client to add
     * @throws SQLException if any database operations fail
     */
    public void addClient(ClientAccount client) throws SQLException {
        log.info("Adding client {}", client.getName());
    }

    /**
     * Get all of the clients in the database, selects all rows from the clients table.
     *
     * @return a list of all of the clients
     * @throws SQLException if any database operations fail
     */
    public List<ClientAccount> getClients() throws SQLException {
        return null;
    }

    /**
     * Add a consultant to the database, inserts one row in the consultants table.
     *
     * @param consultant the consultant to add
     * @throws SQLException if any database operations fail
     */
    public void addConsultant(Consultant consultant) throws SQLException {
        log.info("Adding consultant {}", consultant.getName());
    }

    /**
     * Get all of the consultant in the database, selects all rows from the consultants table.
     *
     * @return a list of all of the consultants
     * @throws SQLException if any database operations fail
     */
    public List<Consultant> getConsultants() throws SQLException {
        return null;
    }

    /**
     * Add a timecard to the database, inserts one row in the timecards table as well as zero or more rows in the billable_hours and non_billable_hours tables. The process is as follows:
     * obtain the consultant id of the consultant the time card is for from the consultants table
     * insert a new record in the timecards table, and capture the generated key for this record
     * for each time entry on the time card insert a record into the billable_hours or non_billable_hours as appropriate
     * including the timecard_id of the newly created timecards record
     *
     * @param timeCard the timecard to add
     * @throws SQLException if any database operations fail
     */
    public void addTimeCard(TimeCard timeCard) throws SQLException {
        log.info("Adding Timecard for consultant {}", timeCard.getConsultant().getName());

    }

    /**
     * Get clients monthly invoice. Select all of the data from the database needed to construct the invoice
     * (billable_hours, consultants, skills, timecards), create the invoice and return it.
     *
     * @param client the client to obtain the invoice line items for
     * @param month  the month of the invoice
     * @param year   the year of the invoice
     * @return the clients invoice for the month
     * @throws SQLException if any database operations fail
     */
    public Invoice getInvoice(ClientAccount client, Month month, int year)
            throws SQLException {
        return null;
    }
}
