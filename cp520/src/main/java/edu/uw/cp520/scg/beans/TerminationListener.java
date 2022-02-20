package edu.uw.cp520.scg.beans;

import java.util.EventListener;

/**
 * Interface for accepting notification of consultant terminations.
 *
 * @author Jesse Ruth
 */
public interface TerminationListener extends EventListener {
    /**
     * Invoked when a consultant is involuntarily terminated.
     *
     * @param evt the termination event
     */
    void forcedTermination(TerminationEvent evt);

    /**
     * Invoked when a consultant is voluntarily terminated.
     *
     * @param evt the termination event
     */
    void voluntaryTermination(TerminationEvent evt);
}
