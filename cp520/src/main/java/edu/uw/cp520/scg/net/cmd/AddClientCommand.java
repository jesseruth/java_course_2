package edu.uw.cp520.scg.net.cmd;

import edu.uw.cp520.scg.domain.ClientAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The command to add a ClientAccount to a list maintained by the server, target type is ClientAccount.
 *
 * @author Jesse Ruth
 */
public final class AddClientCommand extends AbstractCommand<ClientAccount> {

    /**
     * Serial Version id.
     **/
    private static final long serialVersionUID = -7506952140544813694L;

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(AddClientCommand.class);

    /**
     * Construct an AddClientCommand with a target.
     *
     * @param target Construct an AddClientCommand with a target.
     */
    public AddClientCommand(ClientAccount target) {
        super(target);
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        log.info("execute AddClientCommand");
        this.getReceiver().execute(this);
    }
}
