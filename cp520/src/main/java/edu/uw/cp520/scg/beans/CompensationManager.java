package edu.uw.cp520.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Approves or rejects compensation changes. Listens for PropertyChangeEvents on the payRate
 * property, any pay rate increase in excess of will be vetoed. The rejection (veto) or acceptance
 * of each pay rate change will be logged as will any successful pay rate change.
 *
 * @author Jesse Ruth
 */
public final class CompensationManager
  implements PropertyChangeListener, VetoableChangeListener {

  /**
   * Da logger.
   */
  private static final Logger log = LoggerFactory.getLogger(CompensationManager.class);

  /**
   * Processes to final pay rate change.
   *
   * @param propertyChangeEvent a change event for the payRate property
   */
  @Override
  public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
    log.info("propertyChange {}", propertyChangeEvent);
    if (
      StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(propertyChangeEvent.getPropertyName())
    ) {
      log.info(
        "Pay Rate Changed from {} to {} for {}",
        propertyChangeEvent.getOldValue(),
        propertyChangeEvent.getNewValue(),
        ((StaffConsultant) propertyChangeEvent.getSource()).getName()
      );
    }
  }

  /**
   * Rejects any raise over 5%.
   *
   * @param propertyChangeEvent a vetoable change event for the payRate property
   * @throws PropertyVetoException if the change is vetoed
   */
  @Override
  public void vetoableChange(final PropertyChangeEvent propertyChangeEvent)
    throws PropertyVetoException {
    log.info("vetoableChange {}", propertyChangeEvent);
    if (
      StaffConsultant.PAY_RATE_PROPERTY_NAME.equals(propertyChangeEvent.getPropertyName())
    ) {
      final int oldValue = (Integer) propertyChangeEvent.getOldValue();
      final int newValue = (Integer) propertyChangeEvent.getNewValue();
      final int MAX_RATE = 105;
      final int PERCENT = 100;
      if (newValue * PERCENT > oldValue * MAX_RATE) {
        throw new PropertyVetoException("Amount exceed allowed %", propertyChangeEvent);
      }

      log.info(
        "Pay Rate Changed from {} to {} for {}",
        propertyChangeEvent.getOldValue(),
        propertyChangeEvent.getNewValue(),
        ((StaffConsultant) propertyChangeEvent.getSource()).getName()
      );
    }
  }
}
