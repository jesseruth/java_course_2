package app;

import edu.uw.cp520.scg.domain.Invoice;
import edu.uw.cp520.scg.persistent.DbServer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility method to contain reading the database properties and create a server.
 *
 * @author Jesse Ruth
 */
public class GetDbServer {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(GetDbServer.class);
    /**
     * File for database properties
     */
    private static final String PROP_FILE_NAME = "database.properties";
    /**
     * Missing string filler.
     */
    private static final String NA = "NA";
    /**
     * URL Property name
     */
    private static final String DB_URL_PROP = "url";
    /**
     * Username property name
     */
    private static final String DB_USERNAME_PROP = "username";
    /**
     * Password property name
     */
    private static final String DB_PASSWORD_PROP = "password";

    private GetDbServer() {}

    /**
     * Will create a new DbServer.
     *
     * @return New DbServer object
     */
    public static DbServer getServer() {
        Properties prop = new Properties();
        try (
            InputStream in = Invoice.class.getClassLoader()
                .getResourceAsStream(PROP_FILE_NAME)
        ) {
            prop.load(in);
        } catch (final IOException e) {
            e.printStackTrace();
            log.error("Unable to create database server", e);
        }

        return new DbServer(
            prop.getProperty(DB_URL_PROP, NA),
            prop.getProperty(DB_USERNAME_PROP, NA),
            prop.getProperty(DB_PASSWORD_PROP, NA)
        );
    }
}
