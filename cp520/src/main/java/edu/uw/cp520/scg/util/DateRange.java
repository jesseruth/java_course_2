package edu.uw.cp520.scg.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * Encapsulates a range of two dates, inclusive of the start date and end date.
 *
 * @author Jesse Ruth
 */
public final class DateRange {
    /**
     * The end date of this range.
     */
    private final LocalDate endDate;
    /**
     * The start date of this range.
     */
    private final LocalDate startDate;

    /**
     * Construct a DateRange given two date strings in the correct format.
     *
     * @param start
     * @param end
     */
    DateRange(String start, String end) {
        this(LocalDate.parse(start), LocalDate.parse(end));
    }

    /**
     * Construct a DateRange given two dates.
     *
     * @param startDate The Start date.
     * @param endDate   The end date.
     */
    DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Construct a DateRange for the given month, the date range shall span the entire month, from
     * the first day of the month through the last day of the month.
     *
     * @param month
     * @param year
     */
    DateRange(Month month, int year) {
        this(LocalDate.of(year, month, 1), LocalDate.of(year, month, 1));
    }

    /**
     * Returns true if the specified date is within the range start date <= date <= end date.
     *
     * @param date A date.
     * @return True if in range.
     */
    public boolean isInRange(LocalDate date) {
        return !(date.isBefore(this.getStartDate())) && date.isBefore(this.getEndDate());
    }

    /**
     * Returns the end date for this DateRange.
     *
     * @return the end date for this DateRange.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns the start date for this DateRange.
     *
     * @return the start date for this DateRange.
     */
    public LocalDate getStartDate() {
        return startDate;
    }
}