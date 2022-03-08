package edu.uw.cp520.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The EEOC monitors SCG's terminations. Listens for TerminationEvents and maintains a count of
 * each type of termination event, and makes these counts available. All TerminationEvents
 * are logged.
 *
 * @author Jesse Ruth
 */
public class Eeoc implements TerminationListener {

    private static final Logger log = LoggerFactory.getLogger(Eeoc.class);
    /**
     * voluntary termination count
     */
    private int voluntaryTerminationCount = 0;
    /**
     * termination count
     */
    private int forcedTerminationCount = 0;

    /**
     * Create new Eeoc;
     */
    public Eeoc() {}

    /**
     * Gets the voluntary termination count.
     *
     * @return voluntary termination count
     */
    public int voluntaryTerminationCount() {
        return voluntaryTerminationCount;
    }

    /**
     * Gets the forced termination count.
     *
     * @return forced termination count
     */
    public int forcedTerminationCount() {
        return forcedTerminationCount;
    }

    /**
     * Simply prints a message indicating that the consultant was fired and adjusts the forced termination count.
     *
     * @param evt the termination event
     */
    @Override
    public void forcedTermination(TerminationEvent evt) {
        log.info("forcedTermination {}", evt.getConsultant());
        this.forcedTerminationCount++;
    }

    /**
     * Simply prints a message indicating that the consultant quit and adjusts the voluntary
     * termination count.
     *
     * @param evt the termination event
     */
    @Override
    public void voluntaryTermination(TerminationEvent evt) {
        log.info("voluntaryTermination {}", evt.getConsultant());
        this.voluntaryTerminationCount++;
    }
}
