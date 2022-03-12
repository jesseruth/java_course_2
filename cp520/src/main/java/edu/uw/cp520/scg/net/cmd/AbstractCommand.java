package edu.uw.cp520.scg.net.cmd;

import edu.uw.cp520.scg.net.server.CommandProcessor;

import java.io.Serializable;

/**
 * The superclass of all Command objects, implements the command role in the Command design pattern.
 *
 * @param <T> the target type, the type the command operates on
 * @author Jesse Ruth
 */
public abstract class AbstractCommand<T> implements Command<T>, Serializable {
    private T target;
    private CommandProcessor receiver;

    /**
     * Construct an AbstractCommand without a target; called from subclasses.
     */
    public AbstractCommand() { }

    /**
     * Construct an AbstractCommand with a target; called from subclasses.
     *
     * @param target the target
     */
    public AbstractCommand(T target) {
        this.target = target;
    }

    /**
     * Gets the CommandProcessor receiver for this Command.
     *
     * @return the receiver for this Command.
     */
    public final CommandProcessor getReceiver() {
        return this.receiver;
    }

    /**
     * Set the CommandProcessor that will execute this Command.
     *
     * @param receiver CommandProcessor for this command.
     */
    public final void setReceiver(CommandProcessor receiver) {
        this.receiver = receiver;
    }

    /**
     * Return the target of this Command.
     *
     * @return the target.
     */
    public final T getTarget() {
        return this.target;
    }

    /**
     * A string representation of this command.
     *
     * @return A string representation of this command.
     */
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
