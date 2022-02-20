package edu.uw.cp520.scg.beans;

import edu.uw.cp520.scg.domain.Consultant;

import java.io.Serializable;
import java.util.EventObject;

/**
 * Event used to notify listeners of a Consultant's termination.
 *
 * @author Jesse Ruth
 */
public class TerminationEvent extends EventObject implements Serializable {
    /**
     * the consultant being terminated
     */
    private final Consultant consultant;
    /**
     * was the termination voluntary
     */
    private final boolean voluntary;

    /**
     * @param source     the event source
     * @param consultant the consultant being terminated
     * @param voluntary  was the termination voluntary
     */
    public TerminationEvent(final Object source,
                            final Consultant consultant,
                            final boolean voluntary) {
        super(source);
        this.consultant = consultant;
        this.voluntary = voluntary;
    }

    /**
     * Gets the consultant that was terminated.
     *
     * @return the consultant that was terminated
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Gets the voluntary termination status.
     *
     * @return true if a voluntary termination
     */
    public boolean isVoluntary() {
        return voluntary;
    }
}
