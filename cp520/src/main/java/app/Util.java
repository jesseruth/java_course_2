package app;

import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.ext.util.ListFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
    static final List<ClientAccount> ACCOUNT_LIST;
    static final List<Consultant> CONSULTANT_LIST;
    static final List<TimeCard> TIME_CARD_LIST;

    static {
        ACCOUNT_LIST = new ArrayList<>();
        CONSULTANT_LIST = new ArrayList<>();
        TIME_CARD_LIST = new ArrayList<>();
        ListFactory.populateLists(ACCOUNT_LIST, CONSULTANT_LIST, TIME_CARD_LIST);
    }

    private Util() {
    }

    /**
     * Serializes the object to the filename.
     *
     * @param fileName Filename to write
     * @param object   Object to write
     */
    public static void writeObject(final String fileName, Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileName)
            );
            out.writeObject(object);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets a list of objects from a file.
     *
     * @param fileName filename to load.
     * @return A list oj objects from the file.
     */
    public static Object readObject(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            return in.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
