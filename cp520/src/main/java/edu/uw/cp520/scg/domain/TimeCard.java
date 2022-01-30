package edu.uw.cp520.scg.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a time card capable of storing a collection of a consultant's billable and
 * non-billable hours for a week. The TimeCard maintains a collection of ConsultantTime, and provides access to number of hours and time billed to a particular client.
 *
 * @author Jesse Ruth
 */
public final class TimeCard {
    /**
     * Format string for a line header on the time card.
     */
    private static final String LINE_HEADER_FORMAT = String.format("%-28s %-10s  %5s  %s%n"
                    + "---------------------------  ----------  -----  --------------------%n",
            "Account", "Date", "Hours", "Skill");
    /**
     * The consultant.
     **/
    private final Consultant consultant;
    /**
     * Start date for this timecard.
     **/
    private final LocalDate weekStartingDay;
    /**
     * List of consulting hours.
     **/
    private final List<ConsultantTime> consultingHours;


    /**
     * Creates a new instance of TimeCard
     *
     * @param consultant      The Consultant whose information this TimeCard records.
     * @param weekStartingDay The date of the first work day of the week this TimeCard records information for.
     */
    public TimeCard(Consultant consultant, LocalDate weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = weekStartingDay;
        this.consultingHours = new ArrayList<>();
    }

    /**
     * Getter for consultant property.
     *
     * @return value of consultant property.
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Getter for billableHours property.
     *
     * @return value of billableHours property
     */
    public int getTotalBillableHours() {
        return consultingHours.stream().filter(ConsultantTime::isBillable)
                .map(ConsultantTime::getHours)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Getter for totalNonBillableHours property.
     *
     * @return Value of totalNonBillableHours property
     */
    public int getTotalNonBillableHours() {
        return consultingHours.stream().filter(hours -> !hours.isBillable())
                .map(ConsultantTime::getHours)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Getter for consultingHours property.
     *
     * @return Value of consultingHours property
     */
    public List<ConsultantTime> getConsultingHours() {
        return Collections.unmodifiableList(consultingHours);
    }

    /**
     * Add a ConsultantTime object to the collection maintained by this TimeCard.
     *
     * @param consultantTime The ConsultantTime to add
     */
    public void addConsultantTime(ConsultantTime consultantTime) {
        this.consultingHours.add(consultantTime);
    }

    /**
     * Getter for totalHours property.
     *
     * @return Value of totalHours property
     */
    public int getTotalHours() {
        return consultingHours.stream()
                .map(ConsultantTime::getHours)
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Getter for weekStartingDay property.
     *
     * @return Value of weekStartingDay property.
     */
    public LocalDate getWeekStartingDay() {
        return weekStartingDay;
    }

    /**
     * Returns the billable hours (if any) in this TimeCard for the specified Client. Finds all the
     * billable hours in the collection of ConsultantTime objects where the client matched the
     * provided client and returns a new list in containing only these ConsultantTime objects.
     *
     * @param clientName name of the client to extract hours for.
     * @return list of billable hours for the client.
     */
    public List<ConsultantTime> getBillableHoursForClient(String clientName) {
        final ArrayList<ConsultantTime> billableHours = new ArrayList<>();
        for (final ConsultantTime currentTime : consultingHours) {
            if (clientName.equals(currentTime.getAccount().getName()) && currentTime.isBillable()) {
                billableHours.add(currentTime);
            }
        }
        return billableHours;
    }

    /**
     * Create a string representation of this object, consisting of the consultant name and the time
     * card week starting day.
     *
     * @return a string containing the consultant name and the time card week starting day
     */
    @Override
    public String toString() {
        return String.format("%s %s", consultant.getName().toString(), getWeekStartingDay());
    }

    /**
     * Create a string representation of this object, suitable for printing the entire time card.
     *
     * @return this TimeCard as a formatted String.
     */
    public String toReportString() {
        String title = String.format("Consultant: %s              Week Starting: %s%n%n", getConsultant(), getWeekStartingDay());

        String billable = consultingHours.stream()
                .filter(hours -> hours.isBillable())
                .map(hours -> {
                    return String.format("%-28s %s  %5s  %-20s", hours.getAccount().getName(), hours.getDate(), hours.getHours(), hours.getSkillType());
                })
                .collect(Collectors.joining("\n"));

        String nonBillable = consultingHours.stream()
                .filter(hours -> !hours.isBillable())
                .map(hours -> {
                    return String.format("%-28s %s  %5s  %-20s", hours.getAccount().getName(), hours.getDate(), hours.getHours(), hours.getSkillType());
                })
                .collect(Collectors.joining("\n"));
        ;
        StringBuilder reportString = new StringBuilder()
                .append("====================================================================\n")
                .append(title)
                .append("Billable Time:\n")
                .append(LINE_HEADER_FORMAT)
                .append(billable + "\n")
                .append("\nNon-billable:\n")
                .append(LINE_HEADER_FORMAT).append(nonBillable).append("\n")
                .append("\nSummary:\n")
                // 39
                .append(String.format("%-40s  %4d\n", "Total Billable:", getTotalBillableHours()))
                .append(String.format("%-40s  %4d\n", "Total Non-Billable:", getTotalNonBillableHours()))
                .append(String.format("%-40s  %4d\n", "Total Hours:", getTotalHours()))
                .append("====================================================================\n");


        return reportString.toString();
    }
}
