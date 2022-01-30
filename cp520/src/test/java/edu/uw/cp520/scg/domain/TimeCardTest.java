package edu.uw.cp520.scg.domain;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCardTest {
    final String firstName = "Jesse";
    final String middleName = "Stephen";
    final String lastName = "Ruth";
    final LocalDate startDay = LocalDate.of(1927, 5, 5);
    final PersonalName person1 = new PersonalName(firstName, middleName, lastName);
    final Consultant consultant = new Consultant(person1);
    final PersonalName clientContact = new PersonalName(firstName, middleName, lastName);
    final Address address = new Address("125 main", "Seattle", StateCode.WA, "12349");
    final Account billableAccount = new ClientAccount("Some Account", clientContact, address);
    final Account billableAccount2 = new ClientAccount("Other Account", clientContact, address);
    final Account nonBillableAccount = NonBillableAccount.BUSINESS_DEVELOPMENT;


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

        // Add some billable time for 1st consultant
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, billableAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime2 = new ConsultantTime(startDay, billableAccount, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime3 = new ConsultantTime(startDay, billableAccount, Skill.SYSTEM_ARCHITECT, 1);

        // Add some billable time for 2nd consultant
        ConsultantTime consultantTime4 = new ConsultantTime(startDay, billableAccount2, Skill.SOFTWARE_TESTER, 2);
        ConsultantTime consultantTime5 = new ConsultantTime(startDay, billableAccount2, Skill.SYSTEM_ARCHITECT, 1);

        // Add a non-billable account
        ConsultantTime consultantTime6 = new ConsultantTime(startDay, NonBillableAccount.VACATION, Skill.SYSTEM_ARCHITECT, 1);

        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime2);
        timeCard.addConsultantTime(consultantTime3);
        timeCard.addConsultantTime(consultantTime4);
        timeCard.addConsultantTime(consultantTime5);
        timeCard.addConsultantTime(consultantTime6);
        assertEquals(2, timeCard.getBillableHoursForClient("Other Account").size());
    }

    @Test
    void getTotalBillableHoursForClientWithNonbillable() {
        TimeCard timeCard = new TimeCard(consultant, startDay);
        Account oddVacationAccount = new ClientAccount("Vacation", clientContact, address);

        // Add some time
        ConsultantTime consultantTime1 = new ConsultantTime(startDay, oddVacationAccount, Skill.PROJECT_MANAGER, 3);
        ConsultantTime consultantTime6 = new ConsultantTime(startDay, NonBillableAccount.VACATION, Skill.SYSTEM_ARCHITECT, 1);
        timeCard.addConsultantTime(consultantTime1);
        timeCard.addConsultantTime(consultantTime6);
        assertEquals(1, timeCard.getBillableHoursForClient("Vacation").size());
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