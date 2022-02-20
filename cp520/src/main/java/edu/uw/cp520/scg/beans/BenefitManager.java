package edu.uw.cp520.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Tracks changes in employee benefits. Listens for any PropertyChangeEvent and simply logs them.
 * Additionally, Listens for any BenefitEvent and logs those as well. No other actions are taken in
 * response to any event.
 *
 * @author Jesse Ruth
 */
public final class BenefitManager implements PropertyChangeListener, BenefitListener {
    private static final Logger log = LoggerFactory.getLogger(BenefitManager.class);

    public BenefitManager() {
    }

    /**
     * {@docRoot}
     */
    @Override
    public void medicalEnrollment(BenefitEvent evnt) {
        log.info("medicalEnrollment");

    }

    /**
     * {@docRoot}
     */
    @Override
    public void medicalCancellation(BenefitEvent evnt) {
        log.info("medicalCancellation");

    }

    /**
     * {@docRoot}
     */
    @Override
    public void dentalEnrollment(BenefitEvent evnt) {
        log.info("dentalEnrollment");

    }

    /**
     * {@docRoot}
     */
    @Override
    public void dentalCancellation(BenefitEvent evnt) {
        log.info("dentalCancellation");

    }

    /**
     * Logs the change.
     *
     * @param propertyChangeEvent a property change event for the sickLeaveHours or vacationHours,
     *                            or payRate property
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        log.info("PropertyChangeEvent");
    }
}
