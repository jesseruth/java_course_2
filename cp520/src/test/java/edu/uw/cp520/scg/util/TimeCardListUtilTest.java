package edu.uw.cp520.scg.util;

import edu.uw.cp520.scg.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeCardListUtilTest {
    private final String firstName = "Jesse";
    private final String middleName = "Stephen";
    private final String lastName = "Ruth";
    private final LocalDate startDay1 = LocalDate.of(1927, 5, 5);
    private final LocalDate startDay2 = LocalDate.of(1900, 5, 5);
    private final LocalDate startDay3 = LocalDate.of(1999, 5, 5);
    private final PersonalName person1 = new PersonalName(firstName, middleName, lastName);
    private final Consultant consultant1 = new Consultant(new PersonalName("Adam", "Adams"));
    private final Consultant consultant2 = new Consultant(new PersonalName("Middle", "Matt"));
    private final Consultant consultant3 = new Consultant(new PersonalName("Zim", "Zebra"));
    private final Address address = new Address("125 main", "Seattle", StateCode.WA, "12349");
    private final PersonalName clientContact = new PersonalName(firstName, middleName, lastName);
    private final Account billableAccount = new ClientAccount("Some Account", clientContact, address);
    private final ConsultantTime consultantTime1 = new ConsultantTime(startDay1, billableAccount, Skill.PROJECT_MANAGER, 3);
    private final ConsultantTime consultantTime2 = new ConsultantTime(startDay1, billableAccount, Skill.SOFTWARE_TESTER, 2);
    private final ConsultantTime consultantTime3 = new ConsultantTime(startDay1, billableAccount, Skill.SYSTEM_ARCHITECT, 1);
    private final Account billableAccount2 = new ClientAccount("Other Account", clientContact, address);
    private final Account oddVacationAccount = new ClientAccount("Vacation", clientContact, address);
    private final Account nonBillableAccount = NonBillableAccount.BUSINESS_DEVELOPMENT;
    private final ConsultantTime consultantTime4 = new ConsultantTime(startDay1, nonBillableAccount, Skill.PROJECT_MANAGER, 3);
    private final ConsultantTime consultantTime5 = new ConsultantTime(startDay1, nonBillableAccount, Skill.SOFTWARE_TESTER, 2);
    private final ConsultantTime consultantTime6 = new ConsultantTime(startDay1, nonBillableAccount, Skill.SYSTEM_ARCHITECT, 1);
    List<ConsultantTime> consultantTimeList = new ArrayList<>();
    List<TimeCard> timeCardList = new ArrayList<>();
    private TimeCard timeCard1;
    private TimeCard timeCard2;
    private TimeCard timeCard3;

    @BeforeEach
    void setUp() {

        timeCard1 = new TimeCard(consultant2, startDay1);
        timeCard2 = new TimeCard(consultant1, startDay2);
        timeCard3 = new TimeCard(consultant3, startDay3);
        consultantTimeList.add(consultantTime1);
        consultantTimeList.add(consultantTime2);
        consultantTimeList.add(consultantTime3);
        consultantTimeList.add(consultantTime4);
        consultantTimeList.add(consultantTime5);
        consultantTimeList.add(consultantTime6);

        consultantTimeList.stream().forEach(consultantTime -> {
            timeCard1.addConsultantTime(consultantTime);
            timeCard2.addConsultantTime(consultantTime);
            timeCard3.addConsultantTime(consultantTime);
        });

        timeCardList.add(timeCard1);
        timeCardList.add(timeCard2);
        timeCardList.add(timeCard3);

    }

    @AfterEach
    void tearDown() {
        timeCard1 = null;
        timeCard2 = null;
        timeCard3 = null;
    }

    @Test
    void getTimeCardsForConsultant() {
        List<TimeCard> actual = TimeCardListUtil.getTimeCardsForConsultant(timeCardList, consultant1);
        assertEquals(1, actual.size());
    }

    @Test
    void sortByStartDate() {
        timeCardList.forEach(timeCard -> {
            System.out.println(timeCard);
        });
        String actual = timeCardList.stream().map(timeCard -> timeCard.toString()).collect(Collectors.joining());
        assertEquals("Matt, Middle NMN 1927-05-05Adams, Adam NMN 1900-05-05Zebra, Zim NMN 1999-05-05", actual);
        TimeCardListUtil.sortByStartDate(timeCardList);
        timeCardList.forEach(timeCard -> {
            System.out.println(timeCard);
        });
        actual = timeCardList.stream().map(timeCard -> timeCard.toString()).collect(Collectors.joining());

        assertEquals("Adams, Adam NMN 1900-05-05Matt, Middle NMN 1927-05-05Zebra, Zim NMN 1999-05-05", actual);

    }

    @Test
    void sortByConsultantName() {
    }

    @Test
    void getTimeCardsForDateRange() {
    }
}