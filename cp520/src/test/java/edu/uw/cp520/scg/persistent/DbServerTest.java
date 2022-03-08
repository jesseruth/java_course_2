package edu.uw.cp520.scg.persistent;

import static org.junit.jupiter.api.Assertions.*;

import app.DerbyScgDbServer;
import edu.uw.cp520.scg.domain.ClientAccount;
import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import edu.uw.ext.db.derby.EmbeddedNetworkServer;
import edu.uw.ext.util.ListFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * I tried to set this up but was not able to complete it!
 */
@Disabled
class DbServerTest {

    DbServer dbServer;
    EmbeddedNetworkServer server;
    private static final String ENCODING = "ISO-8859-1";
    final List<ClientAccount> clientAccounts = new ArrayList<>();
    final List<Consultant> consultants = new ArrayList<>();
    final List<TimeCard> timeCards = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        String dbName = "scgDb";
        String ddlResource = "scgDb.ddl";
        String username = "student";
        String password = "student";
        File derbyDir = new File("target/derbyDb");
        if (!derbyDir.exists() && !derbyDir.mkdirs()) {
            throw new IOException(
                "Unable to create database directory, " + derbyDir.getAbsolutePath()
            );
        }
        System.setProperty("derby.system.home", "target/derbyDb");
        InputStream inStrm =
            DerbyScgDbServer.class.getClassLoader().getResourceAsStream(ddlResource);
        InputStreamReader ddlReader = new InputStreamReader(inStrm, ENCODING);
        server =
            EmbeddedNetworkServer.createServerFromDdl(
                dbName,
                ddlReader,
                username,
                password,
                true,
                false
            );
        dbServer =
            new DbServer("jdbc:derby://localhost:1527/memory:scgDb", username, password);
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
    }

    @AfterEach
    void tearDown() {
        server.shutdown();
        server = null;
    }

    @Test
    void addAndGetClient() {
        Address address = new Address("123 main", "Burien", StateCode.WA, "1222");
        ClientAccount clientAccount = new ClientAccount(
            "Account",
            new PersonalName("Jim", "Bob"),
            address
        );
        List<ClientAccount> clientAccounts = new ArrayList<>();
        assertEquals(0, clientAccounts.size());
        try {
            clientAccounts.addAll(dbServer.getClients());
            assertEquals(0, clientAccounts.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dbServer.addClient(clientAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            clientAccounts.addAll(dbServer.getClients());
            assertEquals(1, clientAccounts.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addAndGetConsultant() {
        Consultant consultant = new Consultant(new PersonalName("Jim", "bob"));
        List<Consultant> consultants = new ArrayList<>();
        assertEquals(0, consultants.size());
        try {
            consultants.addAll(dbServer.getConsultants());
            assertEquals(0, consultants.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dbServer.addConsultant(consultant);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            consultants.addAll(dbServer.getConsultants());
            assertEquals(1, consultants.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTimeCard() {
        timeCards.forEach(timeCard -> {
            try {
                dbServer.addTimeCard(timeCard);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void getInvoice() {}
}
