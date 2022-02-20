package edu.uw.cp520.scg.beans;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for modifying the pay rate, sick leave and vacation hours of staff consultants.
 * Provide methods for registration of BenefitListeners, and TerminationListeners.
 *
 * @author Jesse Ruth
 */
public final class HumanResourceManager {

  private static final Logger log = LoggerFactory.getLogger(HumanResourceManager.class);

  /**
   * Property change support helper.
   */
  private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  /**
   * Sets the pay rate for a staff consultant and logs whether the pay rate change was approved
   * or rejected (vetoed).
   *
   * @param staffConsultant the consultant
   * @param newPayRate      the new pay rate for the consultant
   */
  public void adjustPayRate(StaffConsultant staffConsultant, int newPayRate) {
    log.info("adjustPayRate {} {}", staffConsultant, newPayRate);
    try {
      staffConsultant.setPayRate(newPayRate);
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets the sick leave hours for a staff consultant.
   *
   * @param staffConsultant   the consultant
   * @param newSickLeaveHours the new sick leave hours for the consultant
   */
  public void adjustSickLeaveHours(
    StaffConsultant staffConsultant,
    int newSickLeaveHours
  ) {
    log.info("adjustSickLeaveHours {} {}", staffConsultant, newSickLeaveHours);
    staffConsultant.setSickLeaveHours(newSickLeaveHours);
  }

  /**
   * Sets the vacation hours for a staff consultant.
   *
   * @param staffConsultant  the consultant
   * @param newVacationHours the new vacation hours for the consultant
   */
  public void adjustVacationHours(StaffConsultant staffConsultant, int newVacationHours) {
    log.info("adjustVacationHours {} {}", staffConsultant, newVacationHours);
    staffConsultant.setVacationHours(newVacationHours);
  }

  /**
   * Accepts the resignation of a consultant and fires a voluntary termination event for the
   * consultant.
   *
   * @param consultant the consultant resigning
   */
  public void acceptResignation(StaffConsultant consultant) {
    log.info("acceptResignation {}", consultant);
  }

  /**
   * Fires an involuntary termination event for the staff consultant. Terminates the employment
   * of a consultant and fires a forced termination event for the consultant.
   *
   * @param consultant the consultant being terminated
   */
  public void terminate(StaffConsultant consultant) {
    log.info("terminate {}", consultant);
  }

  /**
   * Adds a termination listener.
   *
   * @param l the listener to add
   */
  public void addTerminationListener(TerminationListener l) {
    log.info("addTerminationListener {}", l);
  }

  /**
   * Removes a termination listener.
   *
   * @param l the listener to remove
   */
  public void removeTerminationListener(TerminationListener l) {
    log.info("removeTerminationListener {}", l);
  }

  /**
   * Enroll a consultant in medical, and fires a medical enrollment event.
   *
   * @param staffConsultant the consultant enrolling in medical
   */
  public void enrollMedical(StaffConsultant staffConsultant) {
    log.info("enrollMedical {}", staffConsultant);
  }

  /**
   * Cancel a consultant's enrollment in medical, and fires a medical cancellation event.
   *
   * @param staffConsultant the consultant cancelling medical
   */
  public void cancelMedical(StaffConsultant staffConsultant) {
    log.info("cancelMedical {}", staffConsultant);
  }

  /**
   * Enroll a consultant in dental, and fires a dental enrollment event.
   *
   * @param staffConsultant the consultant enrolling in dental
   */
  public void enrollDental(StaffConsultant staffConsultant) {
    log.info("enrollDental {}", staffConsultant);
  }

  /**
   * Cancel a consultant's enrollment in dental, and fires a dental cancellation event
   *
   * @param staffConsultant the consultant cancelling dental
   */
  public void cancelDental(StaffConsultant staffConsultant) {
    log.info("cancelDental {}", staffConsultant);
  }

  /**
   * Adds a benefit listener.
   *
   * @param bm the listener to add
   */
  public void addBenefitListener(BenefitListener bm) {
    log.info("addBenefitListener {}", bm);
  }

  /**
   * Removes a benefit listener.
   *
   * @param l Benefit Listener to remove
   */
  public void removeBenefitListener(BenefitListener l) {
    log.info("removeBenefitListener {}", l);
  }
}
