package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.ext.util.ListFactory;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create serialized lists.
 *
 * @author Jesse Ruth
 */
public class InitLists {

    public static final String TIME_CARDS_SER = "TimeCardList.ser";
    public static final String CLIENTS_SER = "ClientList.ser";
    public static final String CONSULTANTS_SER = "ConsultantList.ser";
    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(InitLists.class);

    /**
     * Prevent instantiation.
     */
    private InitLists() {}

    /**
     * The application method.
     *
     * @param args Command line arguments.
     * @throws Exception if raised
     */
    public static void main(final String[] args) throws Exception {
        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        log.info("Populating Lists");
        Util.writeObject(TIME_CARDS_SER, timeCards);
        Util.writeObject(CONSULTANTS_SER, consultants);
        Util.writeObject(CLIENTS_SER, accounts);
    }
}
