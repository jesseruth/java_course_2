package edu.uw.cp520.scg.domain;

import java.time.LocalDate;

/**
 * Encapsulates a single billable item to be included in an invoice. The InvoiceLineItem includes:
 * date the service was provided,
 * name of consultant providing the service
 * the service/skill provided
 * number of hours
 *
 * @author Jesse Ruth
 */
public final class InvoiceLineItem {

  /**
   * The date of this line item.
   **/
  private final LocalDate date;
  /**
   * Consultant for this line item.
   **/
  private final Consultant consultant;
  /**
   * Skill for this line item.
   **/
  private final Skill skill;
  /**
   * Hours for this line item.
   **/
  private final int hours;

  /**
   * Construct an InvoiceLineItem.
   *
   * @param date       The date of this line item.
   * @param consultant Consultant for this line item.
   * @param skill      Skill for this line item.
   * @param hours      Hours for this line item.
   */
  InvoiceLineItem(LocalDate date, Consultant consultant, Skill skill, int hours) {
    this.date = date;
    this.consultant = consultant;
    this.skill = skill;
    this.hours = hours;
  }

  /**
   * Get the date for this line item.
   *
   * @return The Date.
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Get the consultant for this line item.
   *
   * @return The consultant.
   */
  public Consultant getConsultant() {
    return consultant;
  }

  /**
   * Get the skill for this line item.
   *
   * @return The Skill
   */
  public Skill getSkill() {
    return skill;
  }

  /**
   * Get the hours for this line item.
   *
   * @return The hours.
   */
  public int getHours() {
    return hours;
  }

  /**
   * Get the charge for this line item.
   *
   * @return The charge.
   */
  public int getCharge() {
    return this.hours * skill.getRate();
  }

  /**
   * Print the date, consultant, skill, hours and charge for this line item.
   *
   * @return Formatted string.
   */
  @Override
  public String toString() {
    return (
      "InvoiceLineItem{" +
      "date=" +
      getDate() +
      ", consultant=" +
      getConsultant() +
      ", skill=" +
      getSkill() +
      ", hours=" +
      getHours() +
      '}'
    );
  }
}
