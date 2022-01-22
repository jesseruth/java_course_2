package edu.uw.cp520.scg.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A consultants time, maintains date, skill, account and hours data. This represent a single time
 * entry on a time card.
 *
 * @author Jesse Ruth
 */
public final class ConsultantTime {
    private LocalDate date;
    private Account account;
    private Skill skillType;
    private int hours;

    /**
     * Creates a new instance of ConsultantTime.
     *
     * @param date      The date this instance occurred.
     * @param account   The account to charge the hours to; either a Client or NonBillableAccount.
     * @param skillType The skill type.
     * @param hours     The number of hours, which must be positive.
     * @throws IllegalArgumentException if the hours are <= 0.
     */
    public ConsultantTime(LocalDate date, Account account, Skill skillType, int hours) throws IllegalArgumentException {
        if (hours <= 0) throw new IllegalArgumentException("Hours must be grater than 0");
        this.date = date;
        this.account = account;
        this.skillType = skillType;
        this.hours = hours;
    }

    /**
     * Getter for date property.
     *
     * @return Value of date property.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Setter for date property.
     *
     * @param date new value of date property
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getter for account property.
     *
     * @return Value of account property.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Setter for account property.
     *
     * @param account new value of account property
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Getter for skillType property.
     *
     * @return Value of skillType property.
     */
    public Skill getSkillType() {
        return skillType;
    }

    /**
     * Getter for hours property.
     *
     * @return Value of hours property.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Setter for hours property.
     *
     * @param hours new value of hours property
     */
    public void setHours(int hours) throws IllegalArgumentException {
        if (hours <= 0) throw new IllegalArgumentException("Hours must be grater than 0");
        this.hours = hours;
    }

    /**
     * Determines if the time is billable.
     *
     * @return true if the time is billable otherwise false.
     */
    public boolean isBillable() {
        return getAccount().isBillable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsultantTime)) return false;
        ConsultantTime that = (ConsultantTime) o;
        return hours == that.hours && Objects.equals(date, that.date) && Objects.equals(account, that.account) && skillType == that.skillType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, account, skillType, hours);
    }
}
