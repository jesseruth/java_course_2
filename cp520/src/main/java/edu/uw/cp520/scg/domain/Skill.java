package edu.uw.cp520.scg.domain;

/**
 * Skill that a client may be billed for, consists of a name and an hourly rate.
 *
 * @author Jesse Ruth
 */
public enum Skill {
    PROJECT_MANAGER("Project manager skill", 250),

    SOFTWARE_ENGINEER("Engineer skill", 150),

    SOFTWARE_TESTER("Tester skill", 100),

    SYSTEM_ARCHITECT("Architect skill", 200),

    UNKNOWN_SKILL("Unknown skill", 0);
    final String label;
    final int rate;

    /**
     * Skill constructor.
     * @param label Friendly value
     * @param rate Hourly rate
     */
    Skill(String label, int rate) {
        this.label = label;
        this.rate = rate;
    }

    /**
     * Returns the friendly name for this enumerated value.
     * @return the friendly name for this enumerated value
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Getter for rate property.
     * @return Value of rate property.
     */
    public int getRate() {
        return rate;
    }
}
