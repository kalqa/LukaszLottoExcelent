package pl.lotto.resultannouncer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Document(collection = "result_announcer")
class ResultAnnouncer {
    @Id
    UUID uuid;
    Set<Integer> inputNumbers;
    LocalDateTime drawDateTime;

    ResultAnnouncer(UUID uuid, Set<Integer> inputNumbers, LocalDateTime drawDateTime) {
        this.uuid = uuid;
        this.inputNumbers = inputNumbers;
        this.drawDateTime = drawDateTime;
    }
}
