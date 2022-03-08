package edu.uw.cp520.scg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeCardConsultantComparatorTest {

    private final PersonalName consultContact = new PersonalName("John", "Doe");
    private final Consultant consultant1 = new Consultant(consultContact);
    private final Consultant otherConsultant = new Consultant(
        new PersonalName("Jane", "Doe")
    );
    private final LocalDate localDate1 = LocalDate.of(2022, 1, 1);
    private TimeCard timeCard1;
    private TimeCard timeCard2;
    private TimeCard timeCard3;
    private TimeCardConsultantComparator timeCardConsultantComparator;

    @BeforeEach
    void setUp() {
        timeCard1 = new TimeCard(consultant1, localDate1);
        timeCard2 = new TimeCard(consultant1, localDate1);
        timeCard3 = new TimeCard(otherConsultant, localDate1);
        timeCardConsultantComparator = new TimeCardConsultantComparator();
    }

    @AfterEach
    void tearDown() {
        timeCard1 = null;
        timeCard2 = null;
    }

    @Test
    void compare() {
        int actual = timeCardConsultantComparator.compare(timeCard1, timeCard2);
        assertEquals(0, actual);
        actual = timeCardConsultantComparator.compare(timeCard1, timeCard3);
        assertTrue(actual > 0);
        actual = timeCardConsultantComparator.compare(timeCard3, timeCard1);
        assertTrue(actual < 0);
    }
}
