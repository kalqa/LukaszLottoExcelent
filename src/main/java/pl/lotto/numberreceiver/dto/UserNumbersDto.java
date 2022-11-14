package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record UserNumbersDto(UUID uuid, Set<Integer> numbersFromUser, LocalDateTime dateTime) {
}
