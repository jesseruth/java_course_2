package edu.uw.cp520.scg.net.cmd;

import edu.uw.cp520.scg.domain.Consultant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The command to add a Consultant to a list maintained by the server, target type is Consultant.
 *
 * @author Jesse Ruth
 */
public final class AddConsultantCommand extends AbstractCommand<Consultant>  {

    private static final long serialVersionUID = 5932744383365838307L;
    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(AddConsultantCommand.class);

    /**
     * Construct an AddConsultantCommand with a target.
     * @param target  Construct an AddConsultantCommand with a target.
     */
    public AddConsultantCommand(Consultant target) {

    }
    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        log.info("execute AddConsultantCommand");
        this.getReceiver().execute(this);
    }
}
