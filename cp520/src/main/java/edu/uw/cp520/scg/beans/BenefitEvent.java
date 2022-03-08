package edu.uw.cp520.scg.beans;

import edu.uw.cp520.scg.domain.Consultant;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event used to notify listeners of a Consultant's enrollment or cancellation of medical or
 * dental benefits.
 *
 * @author Jesse Ruth
 */
public final class BenefitEvent extends EventObject {

    /**
     * serial version UID
     */
    private static final long serialVersionUID = 23412123L;

    /**
     * Da Logger;
     */
    private static final Logger log = LoggerFactory.getLogger(BenefitEvent.class);

    /**
     * Enrolling in medical
     */
    private Optional<Boolean> enrolledMedical;
    /**
     * Enrolling in dental
     */
    private Optional<Boolean> enrolledDental;

    /**
     * the consultant being terminated
     */
    private final Consultant consultant;
    /**
     * effective date of cancellation
     */
    private final LocalDate effectiveDate;

    /**
     * @param source the event source
     */
    private BenefitEvent(
        final Object source,
        final Consultant consultant,
        final LocalDate effectiveDate,
        final Optional<Boolean> enrolledMedical,
        final Optional<Boolean> enrolledDental
    ) {
        super(source);
        log.info("New BenefitEvent");
        this.consultant = consultant;
        this.effectiveDate = effectiveDate;
        this.enrolledMedical = enrolledMedical;
        this.enrolledDental = enrolledDental;
    }

    /**
     * Creates a dental cancellation event.
     *
     * @param source        the event source
     * @param consultant    the consultant canceling medical
     * @param effectiveDate effective date of cancellation
     * @return a new event object
     */
    public static BenefitEvent cancelDental(
        Object source,
        Consultant consultant,
        LocalDate effectiveDate
    ) {
        log.info("cancelDental");
        return new BenefitEvent(
            source,
            consultant,
            effectiveDate,
            Optional.empty(),
            Optional.of(false)
        );
    }

    /**
     * Creates a medical cancellation event.
     *
     * @param source        the event source
     * @param consultant    the consultant canceling dental
     * @param effectiveDate effective date of cancellation
     * @return a new event object
     */
    public static BenefitEvent cancelMedical(
        Object source,
        Consultant consultant,
        LocalDate effectiveDate
    ) {
        log.info("cancelMedical");
        return new BenefitEvent(
            source,
            consultant,
            effectiveDate,
            Optional.of(false),
            Optional.empty()
        );
    }

    /**
     * Creates a dental enrollment event.
     *
     * @param source        the event source
     * @param consultant    the consultant enrolling in dental
     * @param effectiveDate effective date of enrollment
     * @return a new event object
     */
    public static BenefitEvent enrollDental(
        Object source,
        Consultant consultant,
        LocalDate effectiveDate
    ) {
        log.info("enrollDental");

        return new BenefitEvent(
            source,
            consultant,
            effectiveDate,
            Optional.empty(),
            Optional.of(true)
        );
    }

    /**
     * Creates a medical enrollment event.
     *
     * @param source        the event source
     * @param consultant    the consultant being terminated
     * @param effectiveDate effective date of enrollment
     * @return a new event object
     */
    public static BenefitEvent enrollMedical(
        Object source,
        Consultant consultant,
        LocalDate effectiveDate
    ) {
        log.info("enrollMedical");
        return new BenefitEvent(
            source,
            consultant,
            effectiveDate,
            Optional.of(true),
            Optional.empty()
        );
    }

    /**
     * Gets the consultant that was terminated.
     *
     * @return the consultant that was terminated
     */
    public Consultant getConsultant() {
        log.info("getConsultant");
        return this.consultant;
    }

    /**
     * Gets the dental enrollment status.
     *
     * @return true enrolled event, false if cancellation, empty if not a
     * dental enrollment event.
     */
    public Optional<Boolean> getDentalStatus() {
        log.info("getDentalStatus");
        return this.enrolledDental;
    }

    /**
     * Gets the medical enrollment status.
     *
     * @return true if enrolled event, false if cancellation,
     * empty if not a medical enrollment event.
     */
    public Optional<Boolean> getMedicalStatus() {
        log.info("getMedicalStatus");
        return this.enrolledMedical;
    }

    /**
     * Gets the effective date.
     *
     * @return the effective date
     */
    public LocalDate getEffectiveDate() {
        log.info("getEffectiveDate");
        return this.effectiveDate;
    }
}
