package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record NumberReceiverDto(UUID uuid, Set<Integer> numbersFromUser, LocalDateTime dateTime) {
}
