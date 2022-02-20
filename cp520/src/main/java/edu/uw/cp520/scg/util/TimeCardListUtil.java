package edu.uw.cp520.scg.util;

import edu.uw.cp520.scg.domain.Consultant;
import edu.uw.cp520.scg.domain.TimeCard;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for processing TimeCard lists.
 *
 * @author Jesse Ruth
 */
public class TimeCardListUtil {

  private static final int DAYS_PER_WEEK = 6;
  private static final TimeCardConsultantComparator consultantComparator = new TimeCardConsultantComparator();

  /**
   * Get a list of TimeCards for the specified consultant.
   *
   * @param timeCards  the list of time cards to extract the sub set from
   * @param consultant the consultant whose TimeCards will be obtained.
   * @return a list of TimeCards for the specified consultant.
   */
  public static List<TimeCard> getTimeCardsForConsultant(
    List<TimeCard> timeCards,
    Consultant consultant
  ) {
    return timeCards
      .stream()
      .filter(timeCard -> timeCard.getConsultant().equals(consultant))
      .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Sorts this list into ascending order, by the start date.
   *
   * @param timeCards the list of time cards to sort
   */
  public static void sortByStartDate(List<TimeCard> timeCards) {
    Collections.sort(timeCards);
  }

  /**
   * Sorts this list into ascending order by consultant name.
   *
   * @param timeCards the list of time cards to sort
   */
  public static void sortByConsultantName(List<TimeCard> timeCards) {
    Collections.sort(timeCards, consultantComparator);
  }

  /**
   * Get a list of TimeCards that cover dates that fall within a date range. Each time card *may*
   * have time entries through out one week beginning with the time card start date.
   *
   * @param timeCards the list of time cards to extract the sub set from
   * @param dateRange The DateRange within which the dates of the returned TimeCards must fall.
   * @return a list of TimeCards having dates fall within the date range.
   */
  public static List<TimeCard> getTimeCardsForDateRange(
    final List<TimeCard> timeCards,
    final DateRange dateRange
  ) {
    return timeCards
      .stream()
      .filter(timeCard ->
        dateRange.isInRange(timeCard.getWeekStartingDay()) ||
        dateRange.isInRange(timeCard.getWeekStartingDay().plusDays(DAYS_PER_WEEK))
      )
      .collect(Collectors.toUnmodifiableList());
  }
}
