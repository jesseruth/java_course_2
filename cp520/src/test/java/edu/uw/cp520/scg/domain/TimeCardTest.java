package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.PersonalName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TimeCardTest {
    String firstName = "Jesse";
    String middleName = "Stephen";
    String lastName = "Ruth";
    LocalDate startDay = LocalDate.of(1927,5,5);
    PersonalName person1 = new PersonalName(firstName, middleName, lastName);
    Consultant consultant = new Consultant(person1);
    PersonalName clientContact =  new PersonalName(firstName, middleName, lastName);
    Account billableAccount = new ClientAccount("Some Account", clientContact);
    Account billableAccount2 = new ClientAccount("Other Account", clientContact);
    Account nonBillableAccount = NonBillableAccount.BUSINESS_DEVELOPMENT;


    @Test
    void getTotalBillableHours() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, billableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, billableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, billableAccount, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        assertEquals(6, timeCard.getTotalBillableHours());
    }

    @Test
    void getTotalBillableHoursForClient() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, billableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, billableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, billableAccount, Skill.SYSTEM_ARCHITECT, 1);
        ConsultantTime consultantTime4 = new ConsultantTime(startDay, billableAccount2, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime5 = new ConsultantTime(startDay, billableAccount2, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        timeCard.addConsultantTime(consultantTime4);
        timeCard.addConsultantTime(consultantTime5);
        assertEquals(2, timeCard.getBillableHoursForClient("Other Account").size());
    }

    @Test
    void getTotalNonBillableHours() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, nonBillableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, nonBillableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        assertEquals(6, timeCard.getTotalNonBillableHours());
    }

    @Test
    void getTotalHours() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, nonBillableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, nonBillableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
        ConsultantTime consultantTime4 = new ConsultantTime(startDay, nonBillableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime5 = new ConsultantTime(startDay, nonBillableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime6 = new ConsultantTime(startDay, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        timeCard.addConsultantTime(consultantTime4);
        timeCard.addConsultantTime(consultantTime5);
        timeCard.addConsultantTime(consultantTime6);
        assertEquals(12, timeCard.getTotalHours());
    }

    @Test
    void getConsultingHours() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, billableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, billableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, billableAccount, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        assertEquals(1, timeCard.getConsultingHours().size());

        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        assertEquals(3, timeCard.getConsultingHours().size());
    }

    @Test
    void addConsultantTime() {
    }

    @Test
    void testToString() {
        TimeCard timeCard = new TimeCard(consultant, startDay);
        assertEquals("Ruth, Jesse Stephen 1927-05-05", timeCard.toString());
    }

    @Test
    void toReportString() {
        TimeCard timeCard = new TimeCard(consultant, startDay);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, billableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, billableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
        ConsultantTime consultantTime4 = new ConsultantTime(startDay, nonBillableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime5 = new ConsultantTime(startDay, nonBillableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime6 = new ConsultantTime(startDay, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        timeCard.addConsultantTime(consultantTime4);
        timeCard.addConsultantTime(consultantTime5);
        timeCard.addConsultantTime(consultantTime6);
        String actual = timeCard.toReportString();

        assertEquals(1053, actual.length());
    }
}