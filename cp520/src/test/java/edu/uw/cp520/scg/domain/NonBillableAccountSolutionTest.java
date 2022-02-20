package edu.uw.cp520.scg.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * JUnit test for NonBillableAccount class.
 */

public final class NonBillableAccountSolutionTest {

  /**
   * Tests the getName method.
   */
  @Test
  public void testGetName() {
    assertEquals("Vacation", NonBillableAccount.VACATION.getName());
  }
}
