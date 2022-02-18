package edu.uw.cp520.scg.beans;

import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.util.PersonalName;

import java.beans.PropertyChangeListener;

public class StaffConsultant extends Consultant {
    public StaffConsultant(PersonalName personalName, int coderInitialPayRate, int initialSickLeaveHours, int initialVacationHours) {
        super(personalName);
    }

    public void addVetoableChangeListener(CompensationManager compMgr) {
    }

    public void addPayRateListener(CompensationManager compMgr) {
    }

    public void addSickLeaveHoursListener(BenefitManager bm) {
    }

    public void addVacationHoursListener(BenefitManager bm) {
    }

    public void addPropertyChangeListener(PropertyChangeListener testPropertyListener) {
    }

    public int getPayRate() {
        return 0;
    }
}
