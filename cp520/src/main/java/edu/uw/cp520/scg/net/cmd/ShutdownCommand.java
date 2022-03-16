package edu.uw.cp520.scg.net.cmd;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Command will cause the CommandProcessor to shutdown the server, this command has no target, so target type
 * is Void.
 *
 * @author Jesse Ruth
 */
public final class ShutdownCommand extends AbstractCommand<Void> {

    /**
     * Serial Version id.
     **/
    private static final long serialVersionUID = -512576961435482346L;

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ShutdownCommand.class);

    /**
     * Construct an ShutdownCommand.
     */
    public ShutdownCommand() {
        super();
    }

    /**
     * {@docRoot}
     */
    @Override
    public void execute() {
        log.info("execute ShutdownCommand");
        this.getReceiver().execute(this);

    }
}
