package edu.uw.cp520.scg.persistent;

import edu.uw.cp520.scg.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
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
     * Add non-billable hours in database.
     */
    private static final String ADD_NON_BILLABLE_HOURS = "INSERT INTO non_billable_hours (account_name, timecard_id, date, hours) VALUES (?, ?, ?, ?)";

    /**
     * Add billable hours in database.
     */
    private static final String ADD_BILLABLE_HOURS = "INSERT INTO billable_hours (client_id, timecard_id, date, hours, skill) VALUES (?, ?, ?, ?, ?)";

    /**
     * Add timecard in database.
     */
    private static final String ADD_TIMECARD = "INSERT INTO timecards (consultant_id, start_date) VALUES (?, ?)";

    /**
     * Get clientId by name
     */
    private static final String GET_CLIENT_ID = "SELECT DISTINCT id FROM clients WHERE name = ?";
    /**
     * Get all clients
     */
    private static final String GET_CLIENTS = "SELECT name, street, city, state, postal_code, contact_last_name, contact_first_name, contact_middle_name FROM clients";
    /**
     * Insert a client.
     */
    private static final String ADD_CLIENT = "INSERT INTO CLIENTS (NAME, STREET, CITY, STATE, POSTAL_CODE, CONTACT_LAST_NAME, CONTACT_FIRST_NAME, CONTACT_MIDDLE_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * Look up consultant by name.
     */
    private static final String GET_CONSULTANT_ID = "SELECT id FROM consultants WHERE last_name = ? AND first_name = ? AND middle_name = ?";
    /**
     * Get all consultants
     */
    private static final String GET_CONSULTANTS = "SELECT last_name, first_name, middle_name FROM consultants";
    /**
     * add a consultant.
     */
    private static final String ADD_CONSULTANT = "INSERT INTO CONSULTANTS (LAST_NAME, FIRST_NAME, MIDDLE_NAME) VALUES (?, ?, ?)";

    /**
     * Query to get line items for an invoice.
     */
    private static final String GET_INVOICE = "SELECT b.date, c.last_name, c.first_name, c.middle_name, b.skill, s.rate, b.hours " +
            "  FROM billable_hours b, consultants c, skills s, timecards t " +
            " WHERE b.date between ? AND ? " +
            "   AND b.client_id = (SELECT DISTINCT id FROM clients WHERE name = ?) " +
            "   AND b.skill = s.name " +
            "   AND b.timecard_id = t.id " +
            "   AND c.id = t.consultant_id ";

    /**
     * Private method to create a database connection
     *
     * @return a Connection
     * @throws SQLException if anything goes wrong.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    /**
     * Holder for dbUrl
     */
    private final String dbUrl;
    /**
     * Holder for username
     */
    private final String username;
    /**
     * Holder for password
     */
    private final String password;

    /**
     * Constructor
     *
     * @param dbUrl    the database URL
     * @param username the database username
     * @param password the database password
     */
    public DbServer(String dbUrl, String username, String password) {
        log.info("\nURL: {} \nUsername: {}\nPassword: {}", dbUrl, username, password);
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Add a client to the database, inserts one row in the clients table.
     *
     * @param client the client to add
     * @throws SQLException if any database operations fail
     */
    public void addClient(ClientAccount client) throws SQLException {
        log.info("Adding client {}", client.getName());
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_CLIENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress().getStreetNumber());
            ps.setString(3, client.getAddress().getCity());
            ps.setString(4, client.getAddress().getState().toString());
            ps.setString(5, client.getAddress().getPostalCode());
            ps.setString(6, client.getContact().getLastName());
            ps.setString(7, client.getContact().getFirstName());
            ps.setString(8, client.getContact().getMiddleName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next(); // assume we successfully inserted
            int clientId = rs.getInt(1);
            log.info("Added client {} with ID: {}", client.getName(), clientId);
        }
    }

    /**
     * Get all of the clients in the database, selects all rows from the clients table.
     *
     * @return a list of all of the clients
     * @throws SQLException if any database operations fail
     */
    public List<ClientAccount> getClients() throws SQLException {

        try (Connection conn = getConnection()) {
            log.info("getClients");
            List<ClientAccount> clients = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_CLIENTS);

            while (rs.next()) {
                String stateString = rs.getString("state");
                log.info("Read State {}", stateString);
                StateCode stateCode = StateCode.valueOf(stateString);
                Address address = new Address(rs.getString("street"), rs.getString("city"), stateCode, rs.getString("postal_code"));
                PersonalName contact = new PersonalName(rs.getString("contact_first_name"), rs.getString("contact_middle_name"), rs.getString("contact_last_name"));
                ClientAccount clientAccount = new ClientAccount(rs.getString("name"), contact, address);
                clients.add(clientAccount);
                log.info("Read Account {}", clientAccount);
            }
            return clients;
        }
    }

    /**
     * Add a consultant to the database, inserts one row in the consultants table.
     *
     * @param consultant the consultant to add
     * @throws SQLException if any database operations fail
     */
    public void addConsultant(Consultant consultant) throws SQLException {
        log.info("Adding consultant {}", consultant.getName());
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    ADD_CONSULTANT,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, consultant.getName().getLastName());
            ps.setString(2, consultant.getName().getFirstName());
            ps.setString(3, consultant.getName().getMiddleName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next(); // assume we successfully inserted
            int clientId = rs.getInt(1);
            log.info("Added consultant {} with ID: {}", consultant.getName(), clientId);
        }
    }

    /**
     * Get all of the consultant in the database, selects all rows from the consultants table.
     *
     * @return a list of all of the consultants
     * @throws SQLException if any database operations fail
     */
    public List<Consultant> getConsultants() throws SQLException {
        List<Consultant> consultants = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmnt = conn.createStatement()) {
            ResultSet rs = stmnt.executeQuery(GET_CONSULTANTS);
            while (rs.next()) {
                Consultant consultant = new Consultant(new PersonalName(rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name")));
                consultants.add(consultant);
                log.info("Read consultant {}", consultant);
            }
        }

        return consultants;
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
        try (Connection conn = getConnection()) {
            PreparedStatement getConsultantID = conn.prepareStatement(GET_CONSULTANT_ID,
                    Statement.RETURN_GENERATED_KEYS
            );
            PersonalName contact = timeCard.getConsultant().getName();
            getConsultantID.setString(1, contact.getLastName());
            getConsultantID.setString(2, contact.getFirstName());
            getConsultantID.setString(3, contact.getMiddleName());
            ResultSet rs = getConsultantID.executeQuery();
            rs.next(); // assume we successfully inserted
            String consultantID = rs.getString("id");
            log.info(
                    "Got consultantID: {} for {}",
                    consultantID,
                    timeCard.getConsultant().getName()
            );

            PreparedStatement addTimeCard = conn.prepareStatement(ADD_TIMECARD, Statement.RETURN_GENERATED_KEYS);
            addTimeCard.setString(1, consultantID);
            addTimeCard.setString(2, timeCard.getWeekStartingDay().toString());
            addTimeCard.executeUpdate();
            rs = addTimeCard.getGeneratedKeys();
            rs.next(); // assume we successfully inserted
            int timecardID = rs.getInt(1);
            log.info(
                    "Added Timecard {} with ID: {}",
                    timeCard.getConsultant().getName().getLastName(),
                    timecardID
            );

            for (ConsultantTime consultantTime : timeCard.getConsultingHours()) {
                log.info(
                        "\t\tconsultantTime {} Skill {} Is Billable {}",
                        timeCard.getConsultant().getName(),
                        consultantTime.getSkillType(),
                        consultantTime.isBillable()
                );
                if (consultantTime.isBillable()) {
                    PreparedStatement billStatement = conn.prepareStatement(GET_CLIENT_ID);
                    billStatement.setString(1, consultantTime.getAccount().getName());
                    rs = billStatement.executeQuery();
                    rs.next(); // assume we successfully inserted
                    String clientID = rs.getString("id");
                    log.info("Got clientID: {}", clientID);
                    billStatement = conn.prepareStatement(ADD_BILLABLE_HOURS, Statement.RETURN_GENERATED_KEYS);
                    billStatement.setString(1, clientID);
                    billStatement.setString(2, String.valueOf(timecardID));
                    billStatement.setString(3, consultantTime.getDate().toString());
                    billStatement.setString(4, String.valueOf(consultantTime.getHours()));
                    billStatement.setString(5, consultantTime.getSkill().name());

                    billStatement.executeUpdate();
                    rs = billStatement.getGeneratedKeys();
                    rs.next();
                    int consultantTimeID = rs.getInt(1);
                    log.info("Added billable consultant Time ID:{}", consultantTimeID);
                } else {
                    PreparedStatement nonBillStatement = conn.prepareStatement(ADD_NON_BILLABLE_HOURS, Statement.RETURN_GENERATED_KEYS
                    );
                    nonBillStatement.setString(
                            1,
                            ((NonBillableAccount) consultantTime.getAccount()).name()
                    );
                    nonBillStatement.setInt(2, timecardID);
                    nonBillStatement.setString(3, consultantTime.getDate().toString());
                    nonBillStatement.setString(4, String.valueOf(consultantTime.getHours()));
                    nonBillStatement.executeUpdate();
                    rs = nonBillStatement.getGeneratedKeys();
                    rs.next();
                    int consultantTimeID = rs.getInt(1);
                    log.info("Added Non-billable consultant Time ID:{}", consultantTimeID);
                }
            }
        }

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
        try (Connection conn = getConnection(); PreparedStatement stmnt = conn.prepareStatement(GET_INVOICE)) {
            final LocalDate START_DATE = LocalDate.of(year, month, 1);
            final LocalDate END_DATE = START_DATE.plusDays(START_DATE.lengthOfMonth() - 1);
            stmnt.setString(1, String.valueOf(START_DATE));
            stmnt.setString(2, String.valueOf(END_DATE));
            stmnt.setString(3, client.getName());
            ResultSet rs = stmnt.executeQuery();
            Invoice invoice = new Invoice(client, month, year);

            while (rs.next()) {
                final Consultant consultant = new Consultant(new PersonalName(rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name")));
                final InvoiceLineItem lineItem = new InvoiceLineItem(LocalDate.parse(rs.getString("date")), consultant, Skill.valueOf(rs.getString("skill")), rs.getInt("hours"));
                invoice.addLineItem(lineItem);
                log.info("Added invoice lineItem {}", lineItem);
            }
            return invoice;
        }
    }
}
