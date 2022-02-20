package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uw.cp520.scg.util.Address;
import edu.uw.cp520.scg.util.PersonalName;
import edu.uw.cp520.scg.util.StateCode;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for the ConsultantTime class.
 */
public final class ConsultantTimeSolutionTest {

  /** Constant for test year. */
  private static final int TEST_YEAR = 2004;
  /** Constant for start day. */
  private static final int START_DAY = 5;
  /** Constant for hours per day. */
  private static final int HOURS_PER_DAY = 8;
  /** String constant for "FooBar Enterprises". */
  private static final String FOOBAR = "FooBar Enterprises";
  /** String constant for "Client". */
  private static final String CLIENT = "Client";
  /** String constant for "J.". */
  private static final String J_DOT = "J.";
  /** String constant for "Random". */
  private static final String RANDOM = "Random";
  /** Test street address. */
  private static final String STREET_NUMBER = "1024 Kilobyte Dr.";
  /** Test city. */
  private static final String CITY = "Silicone Gulch";
  /** Test ZIP code. */
  private static final String ZIP_CODE = "94105";

  /** ConsultantTime instance for test. */
  private ConsultantTime consultanttime;
  /** LocalDate for test start date. */
  private final LocalDate date = LocalDate.of(TEST_YEAR, Month.JANUARY, START_DAY);
  /** Test start date. */
  /** ClientAccount instance for test. */
  private final ClientAccount client = new ClientAccount(
    FOOBAR,
    new PersonalName(CLIENT, J_DOT, RANDOM),
    new Address(STREET_NUMBER, CITY, StateCode.CA, ZIP_CODE)
  );

  /**
   * Perform test setup.
   */
  @BeforeEach
  public void setUp() {
    consultanttime =
      new ConsultantTime(date, client, Skill.PROJECT_MANAGER, HOURS_PER_DAY);
  }

  /**
   * Perform test tear down.
   */
  @AfterEach
  public void tearDown() {
    consultanttime = null;
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Assertions.assertThrows(
      IllegalArgumentException.class,
      () -> {
        consultanttime =
          new ConsultantTime(
            date,
            NonBillableAccount.SICK_LEAVE,
            Skill.UNKNOWN_SKILL,
            -HOURS_PER_DAY
          );
      }
    );
  }

  /**
   * Tests the getDate and setDate methods.
   */
  @Test
  public void testSetGetDate() {
    final LocalDate[] tests = { LocalDate.now(), LocalDate.ofEpochDay(0), null };

    for (int i = 0; i < tests.length; i++) {
      consultanttime.setDate(tests[i]);
      assertEquals(tests[i], consultanttime.getDate());
    }
  }

  /**
   * Tests the getAccount and setAccount methods.
   */
  @Test
  public void testSetGetAccount() {
    final Account[] tests = { client, null };

    for (int i = 0; i < tests.length; i++) {
      consultanttime.setAccount(tests[i]);
      assertEquals(tests[i], consultanttime.getAccount());
    }
  }

  /**
   * Tests the isBillable method.
   */
  @Test
  public void testIsBillable() {
    consultanttime =
      new ConsultantTime(date, client, Skill.PROJECT_MANAGER, HOURS_PER_DAY);
    assertTrue(consultanttime != null);
    assertTrue(consultanttime.isBillable());
    // Test a non-billable account
    consultanttime =
      new ConsultantTime(
        date,
        NonBillableAccount.SICK_LEAVE,
        Skill.UNKNOWN_SKILL,
        HOURS_PER_DAY
      );
    assertTrue(consultanttime != null);
    assertFalse(consultanttime.isBillable());
  }

  /**
   * Tests the getHours and setHours methods.
   */
  @Test
  public void testSetGetHours() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        // Test the assertion that hours must be positive.
        consultanttime.setHours(-HOURS_PER_DAY);
      }
    );

    int[] tests = { 1, Integer.MAX_VALUE };
    for (int i = 0; i < tests.length; i++) {
      consultanttime.setHours(tests[i]);
      assertEquals(tests[i], consultanttime.getHours());
    }
  }

  /**
   * Tests the getSkill method.
   */
  @Test
  public void testGetSkill() {
    assertEquals(Skill.PROJECT_MANAGER, consultanttime.getSkill());
  }

  /**
   * Tests the toString method.
   */
  @Test
  public void testPrint() {
    consultanttime =
      new ConsultantTime(date, client, Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY);
    assertNotNull(consultanttime.toString());
  }
}
