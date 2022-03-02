package edu.uw.cp520.scg.beans;

import static org.junit.jupiter.api.Assertions.*;

import edu.uw.cp520.scg.util.PersonalName;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StaffConsultantTest {

  StaffConsultant staffConsultant;
  StaffConsultant staffConsultant2;
  StaffConsultant staffConsultant3;
  private final String FIRST_NAME = "Jesse";
  private final String LAST_NAME = "RUTH";
  private final int RATE = 100;
  private final int SICK_LEAVE = 33;
  private final int VACATION = 50;

  private class TestPropertyChange
    implements PropertyChangeListener, VetoableChangeListener {

    public int propertyChanges = 0;
    public int vetoChanges = 0;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      propertyChanges++;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
      vetoChanges++;
    }
  }

  TestPropertyChange testPropertyChange;

  @BeforeEach
  void setUp() {
    staffConsultant =
      new StaffConsultant(
        new PersonalName(FIRST_NAME, LAST_NAME),
        RATE,
        SICK_LEAVE,
        VACATION
      );
    staffConsultant2 =
      new StaffConsultant(
        new PersonalName(FIRST_NAME, LAST_NAME),
        RATE,
        SICK_LEAVE,
        VACATION
      );
    staffConsultant3 =
      new StaffConsultant(new PersonalName(FIRST_NAME, LAST_NAME), RATE, SICK_LEAVE, 2);
    testPropertyChange = new TestPropertyChange();
    staffConsultant.addPayRateListener(testPropertyChange);
    staffConsultant.addSickLeaveHoursListener(testPropertyChange);
    staffConsultant.addPropertyChangeListener(testPropertyChange);
    staffConsultant.addVetoableChangeListener(testPropertyChange);
    staffConsultant.addVacationHoursListener(testPropertyChange);
  }

  @AfterEach
  void tearDown() {
    staffConsultant = null;
    staffConsultant2 = null;
    staffConsultant3 = null;
  }

  @Test
  void removePropertyChangeListener() {
    assertEquals(0, testPropertyChange.propertyChanges);
    staffConsultant.setSickLeaveHours(20);
    staffConsultant.setVacationHours(25);
    assertEquals(4, testPropertyChange.propertyChanges);
    staffConsultant.removePropertyChangeListener(testPropertyChange);
    staffConsultant.setSickLeaveHours(20);
    staffConsultant.setVacationHours(25);
    assertEquals(4, testPropertyChange.propertyChanges);
  }

  @Test
  void removePayRateListener() {
    assertEquals(0, testPropertyChange.propertyChanges);
    assertEquals(0, testPropertyChange.vetoChanges);

    try {
      staffConsultant.setPayRate(20);
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    assertEquals(2, testPropertyChange.propertyChanges);
    assertEquals(1, testPropertyChange.vetoChanges);

    staffConsultant.removePayRateListener(testPropertyChange);
    staffConsultant.removeVetoableChangeListener(testPropertyChange);

    try {
      staffConsultant.setPayRate(20);
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    assertEquals(1, testPropertyChange.vetoChanges);
    assertEquals(2, testPropertyChange.propertyChanges);
  }

  @Test
  void getSickLeaveHours() {
    assertEquals(SICK_LEAVE, staffConsultant.getSickLeaveHours());
  }

  @Test
  void removeSickLeaveHoursListener() {
    assertEquals(0, testPropertyChange.propertyChanges);
    staffConsultant.setSickLeaveHours(20);
    assertEquals(2, testPropertyChange.propertyChanges);
    staffConsultant.removeSickLeaveHoursListener(testPropertyChange);
    staffConsultant.setSickLeaveHours(20);
    assertEquals(2, testPropertyChange.propertyChanges);
  }

  @Test
  void getVacationHours() {
    assertEquals(VACATION, staffConsultant.getVacationHours());
  }

  @Test
  void removeVacationHoursListener() {
    assertEquals(0, testPropertyChange.propertyChanges);
    staffConsultant.setVacationHours(20);
    assertEquals(2, testPropertyChange.propertyChanges);

    staffConsultant.removeVacationHoursListener(testPropertyChange);
    staffConsultant.setVacationHours(22);
    assertEquals(3, testPropertyChange.propertyChanges);
  }

  @Test
  void testEquals() {
    assertTrue(staffConsultant.equals(staffConsultant));
    assertTrue(staffConsultant.equals(staffConsultant2));
    assertTrue(staffConsultant2.equals(staffConsultant));
    assertFalse(staffConsultant2.equals(staffConsultant3));
    assertFalse(staffConsultant.equals(staffConsultant3));
  }

  @Test
  void testHashCode() {
    assertEquals(staffConsultant.hashCode(), staffConsultant2.hashCode());
    assertEquals(staffConsultant.hashCode(), staffConsultant.hashCode());
    assertNotEquals(staffConsultant3.hashCode(), staffConsultant.hashCode());
    assertNotEquals(staffConsultant2.hashCode(), staffConsultant3.hashCode());
  }
}
