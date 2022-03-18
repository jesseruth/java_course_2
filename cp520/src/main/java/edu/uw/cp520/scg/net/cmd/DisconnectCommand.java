package edu.uw.cp520.scg.net.cmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The command to disconnect, this command has no target, so target type is Void.
 *
 * @author Jesse Ruth
 */
public final class DisconnectCommand extends AbstractCommand<Void> {

    /**
     * Serial Version id.
     **/
    private static final long serialVersionUID = -406126408048492472L;

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DisconnectCommand.class);

    /**
     * Construct an DisconnectCommand.
     */
    public DisconnectCommand() {
        super();
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        log.info("execute DisconnectCommand");
        this.getReceiver().execute(this);
    }
}
