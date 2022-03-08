package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.persistent.DbServer;
import edu.uw.ext.util.ListFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The initialize/populate the database, creates the client, consultant and time card lists using
 * ListFactory.populateLists and then populates the database with the data in these lists.
 *
 * @author Jesse Ruth
 */
public final class InitDb {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    /**
     * Variable to keep dbserver
     */
    private static final DbServer dbServer;

    static {
        dbServer = GetDbServer.getServer();
    }

    /**
     * Entry point. Connects to the database and inserts the client, consultant and time card data.
     *
     * @param args not used
     * @throws Exception if anything goes wrong.
     */
    public static void main(String[] args) throws Exception {
        log.info("Initializing Database");

        // Create lists to be populated by factory
        final List<ClientAccount> clientAccounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(clientAccounts, consultants, timeCards);
        for (ClientAccount clientAccount : clientAccounts) {
            dbServer.addClient(clientAccount);
        }
        for (Consultant consultant : consultants) {
            dbServer.addConsultant(consultant);
        }
        for (TimeCard timeCard : timeCards) {
            dbServer.addTimeCard(timeCard);
        }
        log.info("Populating Lists");
    }
}
