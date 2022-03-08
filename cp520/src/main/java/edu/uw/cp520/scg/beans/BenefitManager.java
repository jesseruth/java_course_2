package edu.uw.cp520.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tracks changes in employee benefits. Listens for any PropertyChangeEvent and simply logs them.
 * Additionally, Listens for any BenefitEvent and logs those as well. No other actions are taken in
 * response to any event.
 *
 * @author Jesse Ruth
 */
public final class BenefitManager implements PropertyChangeListener, BenefitListener {

    /**
     * da Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(BenefitManager.class);

    /**
     * {@docRoot}
     */
    @Override
    public void medicalEnrollment(BenefitEvent evnt) {
        log.info(
            "medicalEnrollment for {} on {}",
            evnt.getConsultant().getName(),
            evnt.getEffectiveDate()
        );
    }

    /**
     * {@docRoot}
     */
    @Override
    public void medicalCancellation(BenefitEvent evnt) {
        log.info(
            "medicalCancellation for {} on {}",
            evnt.getConsultant().getName(),
            evnt.getEffectiveDate()
        );
    }

    /**
     * {@docRoot}
     */
    @Override
    public void dentalEnrollment(BenefitEvent evnt) {
        log.info(
            "dentalEnrollment for {} on {}",
            evnt.getConsultant().getName(),
            evnt.getEffectiveDate()
        );
    }

    /**
     * {@docRoot}
     */
    @Override
    public void dentalCancellation(BenefitEvent evnt) {
        log.info(
            "dentalCancellation for {} on {}",
            evnt.getConsultant().getName(),
            evnt.getEffectiveDate()
        );
    }

    /**
     * Logs the change.
     *
     * @param propertyChangeEvent a property change event for the sickLeaveHours or vacationHours,
     *                            or payRate property
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        log.info(
            "PropertyChangeEvent for {}, change {} to {}",
            propertyChangeEvent.getPropertyName(),
            propertyChangeEvent.getOldValue(),
            propertyChangeEvent.getNewValue()
        );
    }
}
