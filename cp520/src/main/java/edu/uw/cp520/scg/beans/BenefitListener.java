package edu.uw.cp520.scg.beans;

import java.util.EventListener;

/**
 * Interface for accepting notification of consultant changes in medical and dental enrollment.
 *
 * @author Jesse Ruth
 */
public interface BenefitListener extends EventListener {
    /**
     * Invoked when a consultant enrolls in medical.
     *
     * @param event a Benefit event
     */
    void medicalEnrollment(BenefitEvent event);

    /**
     * Invoked when a consultant is cancels medical.
     *
     * @param event a Benefit event
     */
    void medicalCancellation(BenefitEvent event);

    /**
     * Invoked when a consultant enrolls in dental.
     *
     * @param event a Benefit event
     */
    void dentalEnrollment(BenefitEvent event);

    /**
     * Invoked when a consultant cancels dental.
     *
     * @param event a Benefit event
     */
    void dentalCancellation(BenefitEvent event);
}
