package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ConsultantTimeTest {

    final String errorMessage = "Hours must be grater than 0";
    final Address address = new Address("125 main", "Seattle", StateCode.WA, "12349");

    @Test
    void hoursMustBePositive() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        try {
            new ConsultantTime(localDate, account, Skill.PROJECT_MANAGER, 0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    void manageDate() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        ConsultantTime consultantTime = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );

        assertEquals(1978, consultantTime.getDate().getYear());

        LocalDate localDate2 = LocalDate.of(1979, 10, 11);
        consultantTime.setDate(localDate2);
        assertEquals(1979, consultantTime.getDate().getYear());

        assertEquals(Skill.PROJECT_MANAGER, consultantTime.getSkillType());
    }

    @Test
    void setHours() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        ConsultantTime consultantTime = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );

        int hours = 10;
        consultantTime.setHours(hours);
        assertEquals(hours, consultantTime.getHours());

        // Test setting hours to zero
        try {
            consultantTime.setHours(0);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    void isBillable() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        ConsultantTime consultantTime = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );
        assertTrue(consultantTime.isBillable());
    }

    @Test
    void testEquals() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        Account account2 = new ClientAccount(
            "Some Other Account",
            accountContact,
            address
        );
        ConsultantTime consultantTime1 = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );
        ConsultantTime consultantTime2 = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );
        ConsultantTime consultantTime3 = new ConsultantTime(
            localDate,
            account,
            Skill.SOFTWARE_TESTER,
            100
        );
        assertTrue(consultantTime1.equals(consultantTime1));
        assertTrue(consultantTime2.equals(consultantTime1));
        assertTrue(consultantTime2.equals(consultantTime2));

        // Not equal to a string
        String notIt = "notIt";
        assertFalse(consultantTime1.equals(notIt));

        // Change Skill
        assertFalse(consultantTime1.equals(consultantTime3));

        // Change Account
        consultantTime1.setAccount(account2);
        assertFalse(consultantTime1.equals(consultantTime2));

        // Change Date
        consultantTime1.setDate(LocalDate.now());
        assertFalse(consultantTime1.equals(consultantTime2));

        // Change Hours
        consultantTime1.setHours(33);
        assertFalse(consultantTime1.equals(consultantTime2));
    }

    @Test
    void testHashcode() {
        LocalDate localDate = LocalDate.of(1978, 1, 1);
        PersonalName accountContact = new PersonalName("Jesse", "ruth");
        Account account = new ClientAccount("Some Account", accountContact, address);
        Account account2 = new ClientAccount(
            "Some Other Account",
            accountContact,
            address
        );
        ConsultantTime consultantTime1 = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );
        ConsultantTime consultantTime2 = new ConsultantTime(
            localDate,
            account,
            Skill.PROJECT_MANAGER,
            100
        );
        assertEquals(consultantTime1.hashCode(), consultantTime2.hashCode());
        assertEquals(consultantTime1.hashCode(), consultantTime1.hashCode());

        consultantTime1.setAccount(account2);
        assertNotEquals(consultantTime1.hashCode(), consultantTime2.hashCode());
    }
}
