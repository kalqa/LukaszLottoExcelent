package pl.lotto.resultannouncer;

import pl.lotto.resultannouncer.dto.ResultAnnouncerDto;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static pl.lotto.resultannouncer.ResultAnnouncerMessage.WIN;

public class ResultAnnouncerFacade {
    private final DateTimeDrawResult dateTimeDrawResult;
    private final ResultAnnouncerRepository resultAnnouncerRepository;

    public ResultAnnouncerFacade(DateTimeDrawResult dateTimeDrawResult, ResultAnnouncerRepository resultAnnouncerRepository) {
        this.dateTimeDrawResult = dateTimeDrawResult;
        this.resultAnnouncerRepository = resultAnnouncerRepository;
    }

    public ResultAnnouncerDto getResults(UUID uuid) {
            requireNonNull(uuid);
            ResultAnnouncer resultAnnouncer = resultAnnouncerRepository.findResultLottoByUUID(uuid);
            ResultAnnouncer createdResultAnnouncer = new ResultAnnouncer(resultAnnouncer.uuid, resultAnnouncer.inputNumbers, resultAnnouncer.drawDateTime);
            ResultAnnouncer savedResultAnnouncer = resultAnnouncerRepository.save(createdResultAnnouncer);
            return new ResultAnnouncerDto(savedResultAnnouncer.uuid, savedResultAnnouncer.inputNumbers, savedResultAnnouncer.drawDateTime, WIN);
    }

    public ResultAnnouncerDto getResults(LocalDateTime dateTimeLotto) {
        LocalDateTime nexDateTimeLotto = dateTimeDrawResult.generateNextDrawDate();
        if (dateTimeLotto != null && dateTimeLotto.equals(nexDateTimeLotto)) {
            ResultAnnouncer resultAnnouncer = resultAnnouncerRepository.findResultLottoByDate(dateTimeLotto);
            ResultAnnouncer createdResultAnnouncer = new ResultAnnouncer(resultAnnouncer.uuid, resultAnnouncer.inputNumbers, resultAnnouncer.drawDateTime);
            ResultAnnouncer savedResultAnnouncer = resultAnnouncerRepository.save(createdResultAnnouncer);
            return new ResultAnnouncerDto(savedResultAnnouncer.uuid, savedResultAnnouncer.inputNumbers, savedResultAnnouncer.drawDateTime, WIN);
        }
        throw new IllegalArgumentException();
    }
}
