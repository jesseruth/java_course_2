package edu.uw.cp520.scg.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a time card capable of storing a collection of a consultant's billable and
 * non-billable hours for a week. The TimeCard maintains a collection of ConsultantTime, and provides access to number of hours and time billed to a particular client.
 *
 * @author Jesse Ruth
 */
public final class TimeCard {
    Consultant consultant;
    LocalDate weekStartingDay;
    List<ConsultantTime> consultingHours;
    int billableHours;

    /**
     * Creates a new instance of TimeCard
     * @param consultant The Consultant whose information this TimeCard records.
     * @param weekStartingDay The date of the first work day of the week this TimeCard records information for.
     */
    public TimeCard(Consultant consultant, LocalDate weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = weekStartingDay;
        this.consultingHours = new ArrayList<>();
    }

    /**
     * Getter for consultant property.
     * @return value of consultant property.
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Getter for billableHours property.
     * @return value of billableHours property
     */
    public int getTotalBillableHours() {
        return billableHours;
    }

    /**
     * Getter for totalNonBillableHours property.
     * @return Value of totalNonBillableHours property
     */
    public int getTotalNonBillableHours() {
        return 0;
    }

    /**
     * Getter for consultingHours property.
     * @return Value of consultingHours property
     */
    public List<ConsultantTime> getConsultingHours() {
        return consultingHours;
    }

    /**
     * Add a ConsultantTime object to the collection maintained by this TimeCard.
     * @param consultantTime The ConsultantTime to add
     */
    public void addConsultantTime(ConsultantTime consultantTime) {
        this.consultingHours.add(consultantTime);
    }

    /**
     * Getter for totalHours property.
     * @return Value of totalHours property
     */
    public int getTotalHours() {
        return 1111;
    }

    /**
     * Getter for weekStartingDay property.
     * @return Value of weekStartingDay property.
     */
    public LocalDate getWeekStartingDay() {
        return weekStartingDay;
    }

    /**
     * Returns the billable hours (if any) in this TimeCard for the specified Client. Finds all the
     * billable hours in the collection of ConsultantTime objects where the client matched the
     * provided client and returns a new list in containing only these ConsultantTime objects.
     * @param clientName name of the client to extract hours for.
     * @return list of billable hours for the client.
     */
    public List<ConsultantTime> getBillableHoursForClient​(String clientName) {
        return consultingHours;
    }

    /**
     * Create a string representation of this object, consisting of the consultant name and the time
     * card week starting day.
     *
     * @return  a string containing the consultant name and the time card week starting day
     */
    @Override
    public String toString() {
        return consultant.getName().toString();
    }

    /**
     * Create a string representation of this object, suitable for printing the entire time card.
     * @return this TimeCard as a formatted String.
     */
    public String toReportString() {
        return "Report String";
    }
}
