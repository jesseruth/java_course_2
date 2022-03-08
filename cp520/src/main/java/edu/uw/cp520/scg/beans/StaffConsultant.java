package edu.uw.cp520.scg.beans;

import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.util.PersonalName;
import java.beans.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A consultant who is kept on staff (receives benefits). The StaffConsultant has bound properties
 * for vacation hours and sick leave hours and a constrained property for pay rate (allows veto).
 *
 * @author Jesse Ruth
 */
public final class StaffConsultant extends Consultant {

    /**
     * Pay rate property name.
     */
    public static final String PAY_RATE_PROPERTY_NAME = "payRate";
    /**
     * Sick leave property name.
     */
    public static final String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";
    /**
     * Vacation hours property name.
     */
    public static final String VACATION_HOURS_PROPERTY_NAME = "vacationHours";
    /**
     * Serial versions ID.
     */
    private static final long serialVersionUID = -7472617753832885069L;
    /**
     * Da logger
     */
    private static final Logger log = LoggerFactory.getLogger(StaffConsultant.class);
    /**
     * Property change support helper.
     */
    private final PropertyChangeSupport pcs;
    /**
     * Vetoable change support helper.
     */
    private final VetoableChangeSupport vcs;
    /**
     * Sick leave hours balance.
     */
    private int sickLeaveHours;
    /**
     * Vacation hours balance.
     */
    private int vacationHours;
    /**
     * Pay rate in cents.
     */
    private int payRate;

    /**
     * Creates a new instance of StaffConsultant
     *
     * @param personalName the consultant's name
     * @param rate         the pay rate in cents
     * @param sickLeave    the sick leave hours
     * @param vacation     the vacation hours
     */
    public StaffConsultant(
        PersonalName personalName,
        int rate,
        int sickLeave,
        int vacation
    ) {
        super(personalName);
        log.info("New StaffConsultant {}", personalName);
        this.payRate = rate;
        this.sickLeaveHours = sickLeave;
        this.vacationHours = vacation;
        pcs = new PropertyChangeSupport(this);
        vcs = new VetoableChangeSupport(this);
    }

    /**
     * Get the current pay rate.
     *
     * @return the pay rate
     */
    public int getPayRate() {
        log.info("getPayRate {}", payRate);
        return this.payRate;
    }

    /**
     * Set the pay rate. Fires the VetoableChange event and if approved the PropertyChange event.
     *
     * @param newPayRate the pay rate in cents
     * @throws PropertyVetoException if a veto occurs
     */
    public void setPayRate(final int newPayRate) throws PropertyVetoException {
        vcs.fireVetoableChange(PAY_RATE_PROPERTY_NAME, this.payRate, newPayRate);
        final int oldPayRate = this.payRate;
        this.payRate = newPayRate;
        pcs.firePropertyChange(PAY_RATE_PROPERTY_NAME, oldPayRate, newPayRate);
        log.info("setPayRate {}", newPayRate);
    }

    /**
     * Adds a general property change listener.
     *
     * @param listener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        log.info("addPropertyChangeListener");
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove a general property change listener.
     *
     * @param listener the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        log.info("removePropertyChangeListener");
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Adds a payRate property change listener.
     *
     * @param l the listener
     */
    public void addPayRateListener(PropertyChangeListener l) {
        log.info("addPayRateListener");
        pcs.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Removes a payRate property change listener.
     *
     * @param l the listener
     */
    public void removePayRateListener(PropertyChangeListener l) {
        log.info("removePayRateListener");
        pcs.removePropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Adds a vetoable change listener, only applicable to payRate changes.
     *
     * @param l the listener
     */
    public void addVetoableChangeListener(VetoableChangeListener l) {
        log.info("addVetoableChangeListener");
        vcs.addVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Removes a vetoable change listener.
     *
     * @param l the listener
     */
    public void removeVetoableChangeListener(VetoableChangeListener l) {
        log.info("removeVetoableChangeListener");
        vcs.removeVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Get the available sick leave.
     *
     * @return the available sick leave hours
     */
    public int getSickLeaveHours() {
        log.info("getSickLeaveHours {}", sickLeaveHours);
        return this.sickLeaveHours;
    }

    /**
     * Set the sick leave hours. Fires the PropertyChange event.
     *
     * @param sickLeaveHours the available sick leave hours
     */
    public void setSickLeaveHours(int sickLeaveHours) {
        log.info("setSickLeaveHours {}", sickLeaveHours);
        int oldSickHours = this.sickLeaveHours;
        this.sickLeaveHours = sickLeaveHours;
        pcs.firePropertyChange(
            SICK_LEAVE_HOURS_PROPERTY_NAME,
            oldSickHours,
            sickLeaveHours
        );
    }

    /**
     * Adds a sickLeaveHours property change listener.
     *
     * @param l the listener
     */
    public void addSickLeaveHoursListener(PropertyChangeListener l) {
        log.info("addSickLeaveHoursListener");
        pcs.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a sickLeaveHours property change listener.
     *
     * @param l the listener
     */
    public void removeSickLeaveHoursListener(PropertyChangeListener l) {
        log.info("removeSickLeaveHoursListener");
        pcs.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Get the available vacation hours.
     *
     * @return the available vacation hours
     */
    public int getVacationHours() {
        log.info("getVacationHours {}", vacationHours);
        return this.vacationHours;
    }

    /**
     * Set the vacation hours. Fires the PropertyChange event.
     *
     * @param vacationHours the vacation hours
     */
    public void setVacationHours(int vacationHours) {
        log.info("setVacationHours {}", vacationHours);
        int oldVacationHours = this.vacationHours;
        this.vacationHours = vacationHours;
        pcs.firePropertyChange(
            VACATION_HOURS_PROPERTY_NAME,
            oldVacationHours,
            vacationHours
        );
    }

    /**
     * Adds a vacationHours property change listener.
     *
     * @param l the listener
     */
    public void addVacationHoursListener(PropertyChangeListener l) {
        log.info("addVacationHoursListener");
        pcs.addPropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a vacationHours property change listener.
     *
     * @param l the listener
     */
    public void removeVacationHoursListener(PropertyChangeListener l) {
        log.info("removeVacationHoursListener");
        pcs.removePropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }

    /**
     * {@docRoot}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffConsultant)) return false;
        if (!super.equals(o)) return false;

        StaffConsultant that = (StaffConsultant) o;

        if (getSickLeaveHours() != that.getSickLeaveHours()) return false;
        if (getVacationHours() != that.getVacationHours()) return false;
        return getPayRate() == that.getPayRate();
    }

    /**
     * {@docRoot}
     */
    @Override
    public int hashCode() {
        int result = getSickLeaveHours();
        result = 31 * result + getVacationHours();
        result = 31 * result + getPayRate();
        return result;
    }
}
