package edu.uw.cp520.scg.beans;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Consumer;
import javax.swing.event.EventListenerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for modifying the pay rate, sick leave and vacation hours of staff consultants.
 * Provide methods for registration of BenefitListeners, and TerminationListeners.
 *
 * @author Jesse Ruth
 */
public final class HumanResourceManager {

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(HumanResourceManager.class);

    /**
     * List to track event listeners.
     */
    private final EventListenerList listenerList = new EventListenerList();

    /**
     * Sets the pay rate for a staff consultant and logs whether the pay rate change was approved
     * or rejected (vetoed).
     *
     * @param staffConsultant the consultant
     * @param newPayRate      the new pay rate for the consultant
     */
    public void adjustPayRate(
        final StaffConsultant staffConsultant,
        final int newPayRate
    ) {
        log.info("adjustPayRate {} {}", staffConsultant, newPayRate);
        try {
            staffConsultant.setPayRate(newPayRate);
            log.info("Adjustment Approved for {}", staffConsultant.getName());
        } catch (PropertyVetoException e) {
            log.info("Adjustment denied for {}", staffConsultant.getName());
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
    public void adjustVacationHours(
        StaffConsultant staffConsultant,
        int newVacationHours
    ) {
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
        fireTerminationEvent(new TerminationEvent(this, consultant, true));
    }

    /**
     * Fires an involuntary termination event for the staff consultant. Terminates the employment
     * of a consultant and fires a forced termination event for the consultant.
     *
     * @param consultant the consultant being terminated
     */
    public void terminate(StaffConsultant consultant) {
        log.info("terminate {}", consultant);
        fireTerminationEvent(new TerminationEvent(this, consultant, false));
    }

    /**
     * Adds a termination listener.
     *
     * @param l the listener to add
     */
    public void addTerminationListener(TerminationListener l) {
        log.info("addTerminationListener {}", l);
        listenerList.add(TerminationListener.class, l);
    }

    /**
     * Removes a termination listener.
     *
     * @param l the listener to remove
     */
    public void removeTerminationListener(TerminationListener l) {
        log.info("removeTerminationListener {}", l);
        listenerList.remove(TerminationListener.class, l);
    }

    /**
     * Enroll a consultant in medical, and fires a medical enrollment event.
     *
     * @param staffConsultant the consultant enrolling in medical
     */
    public void enrollMedical(StaffConsultant staffConsultant) {
        log.info("enrollMedical {}", staffConsultant);
        fireBenefitEvent(
            BenefitEvent.enrollMedical(this, staffConsultant, LocalDate.now())
        );
    }

    /**
     * Cancel a consultant's enrollment in medical, and fires a medical cancellation event.
     *
     * @param staffConsultant the consultant cancelling medical
     */
    public void cancelMedical(StaffConsultant staffConsultant) {
        log.info("cancelMedical {}", staffConsultant);
        fireBenefitEvent(
            BenefitEvent.cancelMedical(this, staffConsultant, LocalDate.now())
        );
    }

    /**
     * Enroll a consultant in dental, and fires a dental enrollment event.
     *
     * @param staffConsultant the consultant enrolling in dental
     */
    public void enrollDental(StaffConsultant staffConsultant) {
        log.info("enrollDental {}", staffConsultant);
        fireBenefitEvent(
            BenefitEvent.enrollDental(this, staffConsultant, LocalDate.now())
        );
    }

    /**
     * Cancel a consultant's enrollment in dental, and fires a dental cancellation event
     *
     * @param staffConsultant the consultant cancelling dental
     */
    public void cancelDental(StaffConsultant staffConsultant) {
        log.info("cancelDental {}", staffConsultant);
        fireBenefitEvent(
            BenefitEvent.cancelDental(this, staffConsultant, LocalDate.now())
        );
    }

    /**
     * Adds a benefit listener.
     *
     * @param l the listener to add
     */
    public void addBenefitListener(BenefitListener l) {
        log.info("addBenefitListener {}", l);
        listenerList.add(BenefitListener.class, l);
    }

    /**
     * Removes a benefit listener.
     *
     * @param l Benefit Listener to remove
     */
    public void removeBenefitListener(BenefitListener l) {
        log.info("removeBenefitListener {}", l);
        listenerList.add(BenefitListener.class, l);
    }

    /**
     * Private method to contain benefit event logic.
     * @param benefitEvent An Event.
     */
    private void fireBenefitEvent(final BenefitEvent benefitEvent) {
        final BenefitListener[] listeners = listenerList.getListeners(
            BenefitListener.class
        );
        benefitEvent
            .getDentalStatus()
            .ifPresent(dentalStatus -> {
                Arrays
                    .stream(listeners)
                    .sequential()
                    .forEach(listener -> {
                        if (dentalStatus) {
                            listener.dentalEnrollment(benefitEvent);
                        } else {
                            listener.dentalCancellation(benefitEvent);
                        }
                    });
            });
        benefitEvent
            .getMedicalStatus()
            .ifPresent(medicalStatus -> {
                Arrays
                    .stream(listeners)
                    .sequential()
                    .forEach(listener -> {
                        if (medicalStatus) {
                            listener.medicalEnrollment(benefitEvent);
                        } else {
                            listener.medicalCancellation(benefitEvent);
                        }
                    });
            });
    }

    /**
     * Private method to contain termination event.
     * @param terminationEvent A termination event.
     */
    private void fireTerminationEvent(final TerminationEvent terminationEvent) {
        final TerminationListener[] listeners = listenerList.getListeners(
            TerminationListener.class
        );
        final Consumer<TerminationListener> listenerConsumer = terminationEvent.isVoluntary()
            ? l -> l.voluntaryTermination(terminationEvent)
            : l -> l.forcedTermination(terminationEvent);
        Arrays.stream(listeners).forEach(listenerConsumer);
    }
}
