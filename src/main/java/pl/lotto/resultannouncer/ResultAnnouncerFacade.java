package pl.lotto.resultannouncer;

import pl.lotto.resultannouncer.dto.ResultAnnouncerDto;

import java.util.UUID;

import static pl.lotto.resultannouncer.ResultAnnouncerMessage.WIN;

public class ResultAnnouncerFacade {
    private final DateTimeDrawResult dateTimeDrawResult;
    private final ResultAnnouncerRepository resultAnnouncerRepository;

    public ResultAnnouncerFacade(DateTimeDrawResult dateTimeDrawResult, ResultAnnouncerRepository resultAnnouncerRepository) {
        this.dateTimeDrawResult = dateTimeDrawResult;
        this.resultAnnouncerRepository = resultAnnouncerRepository;
    }

    public ResultAnnouncerDto getResults(UUID uuid) {
        if (uuid != null) {
            ResultAnnouncer resultAnnouncer = resultAnnouncerRepository.findResultLottoByDate(dateTimeDrawResult.generateNextDrawDate());
            ResultAnnouncer createdResultAnnouncer = new ResultAnnouncer(resultAnnouncer.uuid, resultAnnouncer.inputNumbers, resultAnnouncer.drawDateTime);
            ResultAnnouncer savedResultAnnouncer = resultAnnouncerRepository.save(createdResultAnnouncer);
            return new ResultAnnouncerDto(savedResultAnnouncer.uuid, savedResultAnnouncer.inputNumbers, savedResultAnnouncer.drawDateTime, WIN);
        }
        throw new IllegalArgumentException();
    }
}
