package edu.uw.cp520.scg.domain;

import java.io.Serializable;

/**
 * Accounts which can not be billed - non-billable accounts, such as sick leave, vacation,
 * or business development.
 *
 * @author Jesse Ruth
 */
public enum NonBillableAccount implements Account, Serializable, Comparable<NonBillableAccount> {
    /**
     * Sick Leave.
     **/
    SICK_LEAVE("Sick leave"),
    /**
     * Vacation.
     **/
    VACATION("Vacation"),
    /**
     * Biz Dev.
     **/
    BUSINESS_DEVELOPMENT("Business development");

    /**
     * The name of this account
     **/
    private final String name;

    /**
     * Private constructor.
     *
     * @param name name of this account
     */
    private NonBillableAccount(String name) {
        this.name = name;
    }

    /**
     * Getter for the name of this account.
     *
     * @return the name of this account.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Determines if account is billable.
     *
     * @return Always return false.
     */
    @Override
    public boolean isBillable() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
