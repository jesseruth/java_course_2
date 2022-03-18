package edu.uw.cp520.scg.net.cmd;

import edu.uw.cp520.scg.domain.TimeCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command that adds a TimeCard to the server's list of TimeCards, target type is TimeCards.
 *
 * @author Jesse Ruth
 */
public final class AddTimeCardCommand extends AbstractCommand<TimeCard> {

    /**
     * Serial Version id.
     **/
    private static final long serialVersionUID = -5416948315790694166L;

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(AddTimeCardCommand.class);

    /**
     * Construct an AddTimeCardCommand with a target.
     *
     * @param target the target of this Command.
     */
    public AddTimeCardCommand(TimeCard target) {
        super(target);
    }

    /**
     * Execute this command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        log.info("execute AddTimeCardCommand");
        this.getReceiver().execute(this);
    }
}
