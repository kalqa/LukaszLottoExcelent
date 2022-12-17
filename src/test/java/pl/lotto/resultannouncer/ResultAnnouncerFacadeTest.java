package pl.lotto.resultannouncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.resultannouncer.dto.ResultAnnouncerDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.time.Month.DECEMBER;
import static org.assertj.core.api.Assertions.assertThat;

class ResultAnnouncerFacadeTest {

    ResultAnnouncerRepository resultAnnouncerRepository;

    @Test
    @DisplayName("return failed when user gave incorrect date time draw")
    public void should_return_failed_when_user_gave_incorrect_date_time_draw() {
        // given
        Set<Integer> numbersFromUser = Set.of(12, 23, 45, 11, 90, 50);
        UUID uuid = UUID.randomUUID();

        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacadeConfiguration()
                .createModuleForTests(resultAnnouncerRepository);
        // when
        ResultAnnouncerDto resultAnnouncer = resultAnnouncerFacade.getResults(uuid);
        // then
        LocalDateTime datetime = LocalDateTime.of(2022, DECEMBER, 14, 12, 0);

        assertThat(resultAnnouncer.dateTime()).isNotEqualTo(datetime);
        assertThat(resultAnnouncer.numbersUser()).isNotEqualTo(numbersFromUser);
    }

    @Test
    @DisplayName("return failed user numbers when user gave incorrect date time draw")
    public void should_return_correct_user_numbers_when_user_gave_correct_date_time_draw() {
        // given
        LocalDateTime datetime = LocalDateTime.of(2022, DECEMBER, 17, 12, 0);
        Set<Integer> inputNumbers = Set.of(12, 23, 45, 11, 90, 50);
        UUID uuid = UUID.randomUUID();

        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerFacadeConfiguration()
                .createModuleForTests(resultAnnouncerRepository);
        // when
        ResultAnnouncerDto resultAnnouncer = resultAnnouncerFacade.getResults(uuid);
        // then

        assertThat(resultAnnouncer.dateTime()).isEqualTo(datetime);
        assertThat(resultAnnouncer.numbersUser()).isEqualTo(inputNumbers);
    }
}