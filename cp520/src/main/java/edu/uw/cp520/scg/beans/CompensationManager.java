package edu.uw.cp520.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.EventListener;

/**
 * Approves or rejects compensation changes. Listens for PropertyChangeEvents on the payRate
 * property, any pay rate increase in excess of will be vetoed. The rejection (veto) or acceptance
 * of each pay rate change will be logged as will any successful pay rate change.
 *
 * @author Jesse Ruth
 */
public class CompensationManager implements PropertyChangeListener, VetoableChangeListener, EventListener {
    private static final Logger log = LoggerFactory.getLogger(CompensationManager.class);

    public CompensationManager() {
        log.info("Create new CompensationManager");
    }

    /**
     * Processes to final pay rate change.
     *
     * @param propertyChangeEvent a change event for the payRate property
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        log.info("propertyChange {}", propertyChangeEvent);
    }

    /**
     * Rejects any raise over 5%.
     *
     * @param propertyChangeEvent a vetoable change event for the payRate property
     * @throws PropertyVetoException if the change is vetoed
     */
    @Override
    public void vetoableChange(PropertyChangeEvent propertyChangeEvent) throws PropertyVetoException {
        log.info("vetoableChange {}", propertyChangeEvent);
    }
}
