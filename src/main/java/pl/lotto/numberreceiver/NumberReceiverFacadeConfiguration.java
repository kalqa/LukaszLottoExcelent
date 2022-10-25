package pl.lotto.numberreceiver;

import org.springframework.context.annotation.Bean;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createModuleForTests(TicketRepository ticketRepository) {
        NumbersValidator numberValidator = new NumbersValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator();
        return new NumberReceiverFacade(numberValidator, ticketRepository, drawDateGenerator);
    }
}
