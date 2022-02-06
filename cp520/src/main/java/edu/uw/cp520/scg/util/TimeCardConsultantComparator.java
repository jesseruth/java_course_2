package edu.uw.cp520.scg.util;

import edu.uw.cp520.scg.domain.TimeCard;

import java.util.Comparator;

/**
 * Compares two TimeCard objects ascending order by (in precedence order) consultant, time card
 * starting date, total billable hours and non-billable hours.
 *
 * @author Jesse Ruth
 */
public final class TimeCardConsultantComparator implements Comparator<TimeCard> {

    /**
     * Compares its two arguments, in ascending order by (in precedence order) consultant, the
     * starting date, total billable hours and lastly total non-billable hours
     *
     * @param t1 the first object to be compared.
     * @param t2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is less
     * than, equal to, or greater than the second.
     */
    @Override
    public int compare(TimeCard t1, TimeCard t2) {
        return 0;
    }
}
