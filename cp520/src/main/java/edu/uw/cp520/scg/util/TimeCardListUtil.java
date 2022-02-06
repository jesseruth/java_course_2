package edu.uw.cp520.scg.util;

import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for processing TimeCard lists.
 *
 * @author Jesse Ruth
 */
public class TimeCardListUtil {
    /**
     * Get a list of TimeCards for the specified consultant.
     *
     * @param timeCards  the list of time cards to extract the sub set from
     * @param consultant the consultant whose TimeCards will be obtained.
     * @return a list of TimeCards for the specified consultant.
     */
    public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards, Consultant consultant) {
        return timeCards.stream()
                .filter(timeCard -> timeCard.getConsultant().equals(consultant))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Sorts this list into ascending order, by the start date.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByStartDate(List<TimeCard> timeCards) {
    }

    /**
     * Sorts this list into ascending order by consultant name.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByConsultantName(List<TimeCard> timeCards) {
    }

    /**
     * Get a list of TimeCards that cover dates that fall within a date range. Each time card *may*
     * have time entries through out one week beginning with the time card start date.
     *
     * @param timeCards the list of time cards to extract the sub set from
     * @param dateRange The DateRange within which the dates of the returned TimeCards must fall.
     * @return a list of TimeCards having dates fall within the date range.
     */
    public static List<TimeCard> getTimeCardsForDateRange(List<TimeCard> timeCards, DateRange dateRange) {
        return timeCards.stream().collect(Collectors.toUnmodifiableList());
    }
}
