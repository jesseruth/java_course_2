package edu.uw.cp520.scg.beans;

public interface BenefitListener {
    void medicalEnrollment(BenefitEvent evnt);

    void medicalCancellation(BenefitEvent evnt);

    void dentalEnrollment(BenefitEvent evnt);

    void dentalCancellation(BenefitEvent evnt);
}
