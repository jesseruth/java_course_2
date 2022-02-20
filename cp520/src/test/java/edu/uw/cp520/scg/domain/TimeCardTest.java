package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeCardTest {

  private final String firstName = "Jesse";
  private final String middleName = "Stephen";
  private final String lastName = "Ruth";
  private final LocalDate startDay = LocalDate.of(1927, 5, 5);
  private final LocalDate startDay2 = LocalDate.of(1900, 5, 5);
  private final PersonalName person1 = new PersonalName(firstName, middleName, lastName);
  private final Consultant consultant = new Consultant(person1);
  private final Address address = new Address(
    "125 main",
    "Seattle",
    StateCode.WA,
    "12349"
  );
  private final PersonalName clientContact = new PersonalName(
    firstName,
    middleName,
    lastName
  );
  private final Account billableAccount = new ClientAccount(
    "Some Account",
    clientContact,
    address
  );
  private final ConsultantTime consultantTime1 = new ConsultantTime(
    startDay,
    billableAccount,
    Skill.PROJECT_MANAGER,
    3
  );
  private final ConsultantTime consultantTime2 = new ConsultantTime(
    startDay,
    billableAccount,
    Skill.SOFTWARE_TESTER,
    2
  );
  private final ConsultantTime consultantTime3 = new ConsultantTime(
    startDay,
    billableAccount,
    Skill.SYSTEM_ARCHITECT,
    1
  );
  private final Account billableAccount2 = new ClientAccount(
    "Other Account",
    clientContact,
    address
  );
  private final Account oddVacationAccount = new ClientAccount(
    "Vacation",
    clientContact,
    address
  );
  private final Account nonBillableAccount = NonBillableAccount.BUSINESS_DEVELOPMENT;
  private final ConsultantTime consultantTime4 = new ConsultantTime(
    startDay,
    nonBillableAccount,
    Skill.PROJECT_MANAGER,
    3
  );
  private final ConsultantTime consultantTime5 = new ConsultantTime(
    startDay,
    nonBillableAccount,
    Skill.SOFTWARE_TESTER,
    2
  );
  private final ConsultantTime consultantTime6 = new ConsultantTime(
    startDay,
    nonBillableAccount,
    Skill.SYSTEM_ARCHITECT,
    1
  );
  private TimeCard timeCard;
  private TimeCard otherTimeCard;
  private TimeCard thirdTimeCard;

  @BeforeEach
  void setup() {
    timeCard = new TimeCard(consultant, startDay);
    // Add Billable Hours time
    timeCard.addConsultantTime(consultantTime1);
    timeCard.addConsultantTime(consultantTime2);
    timeCard.addConsultantTime(consultantTime3);
    timeCard.addConsultantTime(consultantTime4);
    timeCard.addConsultantTime(consultantTime5);
    timeCard.addConsultantTime(consultantTime6);
    otherTimeCard = new TimeCard(consultant, startDay);
    // Add Billable Hours time
    otherTimeCard.addConsultantTime(consultantTime1);
    otherTimeCard.addConsultantTime(consultantTime2);
    otherTimeCard.addConsultantTime(consultantTime3);
    otherTimeCard.addConsultantTime(consultantTime4);
    otherTimeCard.addConsultantTime(consultantTime5);
    otherTimeCard.addConsultantTime(consultantTime6);
    thirdTimeCard =
      new TimeCard(new Consultant(new PersonalName("Joe", "Bob")), startDay2);
  }

  @AfterEach
  void tearDown() {
    timeCard = null;
    otherTimeCard = null;
    thirdTimeCard = null;
  }

  @Test
  void getTotalBillableHours() {
    assertEquals(6, timeCard.getTotalBillableHours());
  }

  @Test
  void getTotalBillableHoursForClient() {
    // Add some billable time for 1st consultant

    // Add some billable time for 2nd consultant
    final ConsultantTime consultantTime4 = new ConsultantTime(
      startDay,
      billableAccount2,
      Skill.SOFTWARE_TESTER,
      2
    );
    final ConsultantTime consultantTime5 = new ConsultantTime(
      startDay,
      billableAccount2,
      Skill.SYSTEM_ARCHITECT,
      1
    );

    // Add a non-billable account
    final ConsultantTime consultantTime6 = new ConsultantTime(
      startDay,
      NonBillableAccount.VACATION,
      Skill.SYSTEM_ARCHITECT,
      1
    );

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
    // Add some time
    final ConsultantTime consultantTime1 = new ConsultantTime(
      startDay,
      oddVacationAccount,
      Skill.PROJECT_MANAGER,
      3
    );
    final ConsultantTime consultantTime6 = new ConsultantTime(
      startDay,
      NonBillableAccount.VACATION,
      Skill.SYSTEM_ARCHITECT,
      1
    );
    timeCard.addConsultantTime(consultantTime1);
    timeCard.addConsultantTime(consultantTime6);
    assertEquals(1, timeCard.getBillableHoursForClient("Vacation").size());
  }

  @Test
  void getTotalNonBillableHours() {
    assertEquals(6, timeCard.getTotalNonBillableHours());
  }

  @Test
  void getTotalHours() {
    assertEquals(12, timeCard.getTotalHours());
  }

  @Test
  void getConsultingHours() {
    // Add some time
    final ConsultantTime consultantTime1 = new ConsultantTime(
      startDay,
      billableAccount,
      Skill.PROJECT_MANAGER,
      3
    );
    final ConsultantTime consultantTime2 = new ConsultantTime(
      startDay,
      billableAccount,
      Skill.SOFTWARE_TESTER,
      2
    );
    final ConsultantTime consultantTime3 = new ConsultantTime(
      startDay,
      billableAccount,
      Skill.SYSTEM_ARCHITECT,
      1
    );
    timeCard.addConsultantTime(consultantTime1);
    assertEquals(7, timeCard.getConsultingHours().size());

    timeCard.addConsultantTime(consultantTime2);
    timeCard.addConsultantTime(consultantTime3);
    assertEquals(9, timeCard.getConsultingHours().size());
  }

  @Test
  void testToString() {
    assertEquals("Ruth, Jesse Stephen 1927-05-05", timeCard.toString());
  }

  @Test
  void toReportString() {
    final String actual = timeCard.toReportString();
    assertEquals(1053, actual.length());
  }

  @Test
  void testCompareTo() {
    final ConsultantTime consultantTime1 = new ConsultantTime(
      startDay2,
      nonBillableAccount,
      Skill.PROJECT_MANAGER,
      3
    );
    final ConsultantTime consultantTime2 = new ConsultantTime(
      startDay,
      billableAccount,
      Skill.PROJECT_MANAGER,
      3
    );
    int actual = timeCard.compareTo(otherTimeCard);
    assertEquals(0, actual);

    timeCard.addConsultantTime(consultantTime1);
    actual = timeCard.compareTo(otherTimeCard);
    assertTrue(actual > 0);

    otherTimeCard.addConsultantTime(consultantTime1);
    actual = timeCard.compareTo(otherTimeCard);
    assertEquals(0, actual);

    otherTimeCard.addConsultantTime(consultantTime2);
    actual = timeCard.compareTo(otherTimeCard);
    assertTrue(actual < 0);

    actual = timeCard.compareTo(thirdTimeCard);
    assertTrue(actual > 0);

    actual = thirdTimeCard.compareTo(timeCard);
    assertTrue(actual < 0);
  }
}
