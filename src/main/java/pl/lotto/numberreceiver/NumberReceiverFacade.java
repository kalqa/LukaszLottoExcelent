package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.NumbersResultMessageDto;
import pl.lotto.numberreceiver.dto.TicketMessageDto;
import pl.lotto.numberreceiver.enums.ValidateMessage;
import pl.lotto.numberreceiver.exceptions.DuplicateNumbersNotFoundException;
import pl.lotto.numberreceiver.exceptions.NumbersNotFoundException;
import pl.lotto.numberreceiver.exceptions.RangeNumbersException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static pl.lotto.numberreceiver.GeneratedTicketMessageProvider.generated_ticket_message_failed;
import static pl.lotto.numberreceiver.NumbersDuplicationCounter.printDuplicatedNumbersInfo;
import static pl.lotto.numberreceiver.NumbersMessageProvider.*;
import static pl.lotto.numberreceiver.TicketIdGenerator.generateHash;
import static pl.lotto.numberreceiver.enums.ValidateMessage.*;

public class NumberReceiverFacade {

    private final NumbersValidator numberValidator;
    private final TicketRepository ticketRepository;

    private final DrawDateGenerator drawDateGenerator;

    NumberReceiverFacade(NumbersValidator numberValidator, TicketRepository ticketRepository, DrawDateGenerator drawDateGenerator) {
        this.numberValidator = numberValidator;
        this.ticketRepository = ticketRepository;
        this.drawDateGenerator = drawDateGenerator;
    }

    public NumbersResultMessageDto inputNumbers(Set<Integer> inputNumbers) {
        boolean validate = numberValidator.validate(inputNumbers);
        if(validate){
            LocalDateTime date = drawDateGenerator.generateDrawDate();
            String hash = generateHash();
            Ticket generatedTicket = generateTicket(inputNumbers, hash, date);
            Ticket ticket = ticketRepository.save(generatedTicket);
            TicketMessageDto ticketMessageDto = new TicketMessageDto(ticket, generated_ticket_message_failed());
            return new NumbersResultMessageDto(ticketMessageDto.ticket().getNumbers(), "all good");
        }
        return new NumbersResultMessageDto(Set.of(), "smth not good");
    }

    public NumbersResultMessageDto isLessThanSixNumbers(Set<Integer> inputNumbers) {
        if (numberValidator.checkLessThanSixNumbers(inputNumbers)) {
            NumbersResultMessageDto notCorrectResults = new NumbersResultMessageDto(inputNumbers, FAILED_MESSAGE);
            return Optional.of(notCorrectResults).orElse(new NumbersResultMessageDto(inputNumbers, SUCCESS_MESSAGE));
        }
        throw new NumbersNotFoundException();
    }

    public NumbersResultMessageDto isEqualsSixNumbers(Set<Integer> inputNumbers) {
        if (numberValidator.checkEqualsSixNumbers(inputNumbers)) {
            NumbersResultMessageDto result = new NumbersResultMessageDto(inputNumbers, CORRECT_SIZE_NUMBERS.name());
            return new NumbersResultMessageDto(inputNumbers, result.message());
        }
        return new NumbersResultMessageDto(inputNumbers, INVALID_MESSAGE);
    }

    public NumbersResultMessageDto isMoreThanSixNumbers(Set<Integer> inputNumbers) {
        if (numberValidator.checkMoreThanSixNumbers(inputNumbers)) {
            return Optional.of(new NumbersResultMessageDto(inputNumbers, NOT_CORRECT_SIZE_NUMBERS.name()))
                    .orElseGet(() -> new NumbersResultMessageDto(inputNumbers, CORRECT_SIZE_NUMBERS.name()));
        }
        return new NumbersResultMessageDto(null, UNKNOWN_SIZE_NUMBERS.name());

    }

    public ValidateMessage isDuplicateNumbers(List<Integer> numbersCheck) {
        NumbersDuplicationChecker numbersFinder = new NumbersDuplicationChecker();
        if (numbersFinder.checkIdenticalNumbers(numbersCheck)) {
            return printDuplicatedNumbersInfo(numbersCheck);
        }
        throw new DuplicateNumbersNotFoundException();
    }

    public ValidateMessage isNumbersNotInRange(Set<Integer> inputNumbers) {
        if (numberValidator.checkNumbersInRange(inputNumbers)) {
            return IN_RANGE_NUMBERS;
        }
        throw new RangeNumbersException();
    }

    public Ticket generateTicket(Set<Integer> inputNumbers, String hash, LocalDateTime date) {
        Ticket ticket = Ticket.builder()
                .hash(hash)
                .numbers(new TreeSet<>(inputNumbers))
                .drawDate(date)
                .build();
        return ticket;
    }
}
