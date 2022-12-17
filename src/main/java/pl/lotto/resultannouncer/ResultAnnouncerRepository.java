package pl.lotto.resultannouncer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ResultAnnouncerRepository extends MongoRepository<ResultAnnouncer, UUID> {
    @Query("{ 'uuid' : ?0 }")
    ResultAnnouncer findResultLottoByUUID(UUID uuid);
    @Query("{ 'drawDateTime' : ?0 }")
    ResultAnnouncer findResultLottoByDate(LocalDateTime drawDateTime);
}
