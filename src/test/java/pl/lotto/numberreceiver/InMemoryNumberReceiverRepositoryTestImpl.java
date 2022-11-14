package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class InMemoryNumberReceiverRepositoryTestImpl implements NumberReceiverRepository {
//    private final Map<UUID, Set<Integer>> databaseInMemory = new HashMap<>();
    private final Map<UUID, UserNumbers> dateTimeInMemory = new HashMap<>();

//    @Override
//    public NumberReceiverDto save(NumberReceiverDto numberReceiver) {
//        databaseInMemory.put(numberReceiver.uuid(), numberReceiver.numbersFromUser());
//        return new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser());
//    }

    @Override
    public UserNumbers save(UserNumbers userNumbers) {
        return dateTimeInMemory.put(userNumbers.uuid, userNumbers);
//        return new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser());
    }

    @Override
    public Set<Integer> findByDate(String dateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        LocalDateTime drawDateTime = LocalDateTime.parse(dateTime, dateFormat);
        return dateTimeInMemory.get(drawDateTime);
    }

    @Override
    public Set<Integer> findByUUID(UUID uuid) {
        return databaseInMemory.get(uuid);
    }
}
