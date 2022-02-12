package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.ext.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InitLists {
    public static final String TIME_CARDS_SER = "TimeCardList.ser";
    public static final String CLIENTS_SER = "ClientList.ser";
    /**
     * This class' logger.
     */
    private static final Logger log = LoggerFactory.getLogger(InitLists.class);

    /**
     * Character encoding to use.
     */
    private static final String ENCODING = "ISO-8859-1";
    /**
     * Prevent instantiation.
     */
    private InitLists() {
    }

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
        log.info("\n{}\n\n{}\n\n{}", accounts, consultants, timeCards);

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(TIME_CARDS_SER)
            );
            out.writeObject(timeCards);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(CLIENTS_SER)
            );
            out.writeObject(accounts);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
