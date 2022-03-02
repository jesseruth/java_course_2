package edu.uw.cp520.scg.persistent;

import edu.uw.cp520.scg.domain.*;
import java.sql.*;
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
   * Database Connection.
   */
  private Connection conn = null;

  /**
   * Constructor
   *
   * @param dbUrl    the database URL
   * @param username the database username
   * @param password the database password
   */
  public DbServer(String dbUrl, String username, String password) {
    log.info("\nURL: {} \nUsername: {}\nPassword: {}", dbUrl, username, password);
    try {
      conn = DriverManager.getConnection(dbUrl, username, password);
    } catch (SQLException e) {
      log.error("Unable to create a connection", e);
    }
  }

  /**
   * Add a client to the database, inserts one row in the clients table.
   *
   * @param client the client to add
   * @throws SQLException if any database operations fail
   */
  public void addClient(ClientAccount client) throws SQLException {
    log.info("Adding client {}", client.getName());
    PreparedStatement ps = conn.prepareStatement(
      "INSERT INTO CLIENTS (NAME, STREET, CITY, STATE, POSTAL_CODE, CONTACT_LAST_NAME, CONTACT_FIRST_NAME, CONTACT_MIDDLE_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
      Statement.RETURN_GENERATED_KEYS
    );
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

  /**
   * Get all of the clients in the database, selects all rows from the clients table.
   *
   * @return a list of all of the clients
   * @throws SQLException if any database operations fail
   */
  public List<ClientAccount> getClients() throws SQLException {
    log.info("getClients");
    Statement stmnt = conn.createStatement();
    ResultSet rs = stmnt.executeQuery("SELECT *" + "  FROM SKILLS");

    while (rs.next()) {
      System.out.print(rs.getInt("RATE") + "   ");
      System.out.println(rs.getString("NAME"));
    }
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
    PreparedStatement ps = conn.prepareStatement(
      "INSERT INTO CONSULTANTS (LAST_NAME, FIRST_NAME, MIDDLE_NAME) VALUES (?, ?, ?)",
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
    PreparedStatement ps = conn.prepareStatement(
      "SELECT id FROM consultants WHERE last_name = ? AND first_name = ? AND middle_name = ?",
      Statement.RETURN_GENERATED_KEYS
    );
    ps.setString(1, timeCard.getConsultant().getName().getLastName());
    ps.setString(2, timeCard.getConsultant().getName().getFirstName());
    ps.setString(3, timeCard.getConsultant().getName().getMiddleName());
    ResultSet rs = ps.executeQuery();
    rs.next(); // assume we successfully inserted
    String consultantID = rs.getString("id");
    log.info(
      "Got consultantID: {} for {}",
      consultantID,
      timeCard.getConsultant().getName()
    );

    ps =
      conn.prepareStatement(
        "INSERT INTO timecards (consultant_id, start_date) VALUES (?, ?)",
        Statement.RETURN_GENERATED_KEYS
      );
    ps.setString(1, consultantID);
    ps.setString(2, timeCard.getWeekStartingDay().toString());
    ps.executeUpdate();
    rs = ps.getGeneratedKeys();
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
        PreparedStatement billStatement = conn.prepareStatement(
          "SELECT DISTINCT id FROM clients WHERE name = ?"
        );
        billStatement.setString(1, consultantTime.getAccount().getName());
        rs = billStatement.executeQuery();
        rs.next(); // assume we successfully inserted
        String clientID = rs.getString("id");
        log.info("Got clientID: {}", clientID);
        billStatement =
          conn.prepareStatement(
            "INSERT INTO billable_hours (client_id, timecard_id, date, hours, skill) VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
          );
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
        PreparedStatement nonBillStatement = conn.prepareStatement(
          "INSERT INTO non_billable_hours (account_name, timecard_id, date, hours) VALUES (?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS
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
