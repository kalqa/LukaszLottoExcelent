package pl.lotto.numberreceiver;

import java.util.Set;
import java.util.UUID;

public interface NumberReceiverRepository {
    UserNumbers save(UserNumbers userNumbers);

    Set<Integer> findByDate(String dateTime);

    Set<Integer> findByUUID(UUID uuid);
}
