package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.persistent.DbServer;
import edu.uw.ext.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The initialize/populate the database, creates the client, consultant and time card lists using
 * ListFactory.populateLists and then populates the database with the data in these lists.
 *
 * @author Jesse Ruth
 */
public final class InitDb {
    private static final String PROP_FILE_NAME = "database.properties";
    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    private static final String DB_URL_PROP = "url";
    private static final String DB_USERNAME_PROP = "username";
    private static final String DB_PASSWORD_PROP = "password";

    private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;
    private static final String NA = "NA";

    private static final DbServer dbServer;

    static {
        Properties prop = new Properties();
        try (
                InputStream in = Invoice.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME)
        ) {
            prop.load(in);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        DB_URL = prop.getProperty(DB_URL_PROP, NA);
        DB_USERNAME = prop.getProperty(DB_USERNAME_PROP, NA);
        DB_PASSWORD = prop.getProperty(DB_PASSWORD_PROP, NA);
        dbServer = new DbServer(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    /**
     * Entry point. Connects to the database and inserts the client, consultant and time card data.
     *
     * @param args not used
     * @throws Exception if anything goes wrong.
     */
    public static void main(String[] args) throws Exception {
        log.info("Initializing Database");
        log.info("\nURL: {} \nUsername: {}\nPassword: {}", DB_URL, DB_USERNAME, DB_PASSWORD);
        // Create lists to be populated by factory
        final List<ClientAccount> clientAccounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(clientAccounts, consultants, timeCards);
        clientAccounts.forEach(clientAccount -> {
            try {
                dbServer.addClient(clientAccount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        consultants.forEach(consultant -> {
            try {
                dbServer.addConsultant(consultant);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        timeCards.forEach(timeCard -> {
            try {
                dbServer.addTimeCard(timeCard);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        log.info("Populating Lists");
//        dbServer.addClient();
//        dbServer.addTimeCard();
//        dbServer.getClients();
    }
}
