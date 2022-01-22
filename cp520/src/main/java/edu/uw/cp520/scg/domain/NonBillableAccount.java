package edu.uw.cp520.scg.domain;

import java.io.Serializable;

/**
 * Accounts which can not be billed - non-billable accounts, such as sick leave, vacation,
 * or business development.
 *
 * @author Jesse Ruth
 *
 */
public enum NonBillableAccount implements Account, Serializable, Comparable<NonBillableAccount>  {
    SICK_LEAVE("Sick leave"),
    VACATION("Vacation"),
    BUSINESS_DEVELOPMENT("Business development");
    final String name;

    NonBillableAccount(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isBillable() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
