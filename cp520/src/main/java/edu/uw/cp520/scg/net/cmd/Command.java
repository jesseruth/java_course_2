package edu.uw.cp520.scg.net.cmd;

import edu.uw.cp520.scg.net.server.CommandProcessor;
import java.io.Serializable;

/**
 * The interface of all Command objects, implements the command role in the Command design pattern.
 *
 * @param <T>
 */
public interface Command<T> extends Serializable {
    /**
     * Gets the CommandProcessor receiver for this Command.
     *
     * @return the receiver for this Command.
     */
    CommandProcessor getReceiver();

    /**
     * Set the CommandProcessor that will execute this Command.
     *
     * @param receiver Set the CommandProcessor that will execute this Command.
     */
    void setReceiver(CommandProcessor receiver);

    /**
     * Return the target of this Command.
     *
     * @return the target.
     */
    T getTarget();

    /**
     * The method called by the receiver. This method must be implemented by subclasses to send a reference to
     * themselves to the receiver by calling receiver.execute(this), this is a key implementation detail as method
     * overloading is used to resolve the method to be invoked by the receiver (CommandProcessor).
     */
    void execute();
}
