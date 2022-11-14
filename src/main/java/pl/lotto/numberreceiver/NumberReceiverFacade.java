package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import pl.lotto.numberreceiver.dto.AllUsersNumbersDto;
import pl.lotto.numberreceiver.dto.NumberReceiverDto;
import pl.lotto.numberreceiver.dto.UserNumbersDto;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.temporal.TemporalAdjusters.next;

public class NumberReceiverFacade {

    private final Clock clock;
    private final NumbersReceiverValidator numberValidator;
    private final NumberReceiverRepository numberReceiverRepository;

    NumberReceiverFacade(Clock clock, NumbersReceiverValidator numberValidator, NumberReceiverRepository numberReceiverRepository) {
        this.clock = clock;
        this.numberValidator = numberValidator;
        this.numberReceiverRepository = numberReceiverRepository;
    }

    public NumberReceiverDto inputNumbers(Set<Integer> numbersFromUser) {
        boolean validate = numberValidator.validate(numbersFromUser);
        if (!validate) {
            return new NumberReceiverDto(null, numbersFromUser, null);
        }
        UUID uuid = UUID.randomUUID();
        LocalDateTime dateTimeDraw = generateNextDrawDate();
        UserNumbers userNumbers = new UserNumbers(uuid, numbersFromUser, dateTimeDraw);
        UserNumbers save = numberReceiverRepository.save(userNumbers);
        return new NumberReceiverDto(save.uuid, save.numbersFromUser, save.dateTimeDraw);
    }

    private LocalDateTime generateNextDrawDate() {
        return LocalDateTime.now(clock)
                .with(next(SATURDAY))
                .withHour(12)
                .withMinute(0);
    }

    public AllUsersNumbersDto userNumbers(LocalDateTime date) {
        return new AllUsersNumbersDto(
                List.of(
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()),
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()),
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()),
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()),
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now()),
                        new UserNumbersDto(UUID.randomUUID(), Set.of(1, 2, 3, 4, 5, 6), LocalDateTime.now())
                )
        );
    }
}
