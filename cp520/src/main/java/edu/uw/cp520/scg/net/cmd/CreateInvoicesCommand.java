package edu.uw.cp520.scg.net.cmd;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The command to create invoices for a specified month, target type is LocalDate.
 *
 * @author Jesse Ruth
 */
public final class CreateInvoicesCommand extends AbstractCommand<LocalDate> {

    /**
     * Serial Version id.
     **/
    private static final long serialVersionUID = 8054828831211198705L;

    /**
     * Da Logger
     */
    private static final Logger log = LoggerFactory.getLogger(
        CreateInvoicesCommand.class
    );

    /**
     * Construct a CreateInvoicesCommand with a target month, which should be obtained by getting the desired month constant from LocalDate.
     *
     * @param target the target month.
     */
    public CreateInvoicesCommand(LocalDate target) {
        super(target);
    }

    /**
     * Execute this command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        log.info("execute CreateInvoicesCommand");
        this.getReceiver().execute(this);
    }
}
