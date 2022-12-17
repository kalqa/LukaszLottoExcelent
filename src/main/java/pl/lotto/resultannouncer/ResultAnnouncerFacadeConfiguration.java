package pl.lotto.resultannouncer;

import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ResultAnnouncerFacadeConfiguration {
    ResultAnnouncerFacade createModuleForTests(ResultAnnouncerRepository resultAnnouncerRepository) {
        Clock clock = Clock.systemDefaultZone();
        DateTimeDrawResult dateTimeDrawResult = new DateTimeDrawResult(clock);
        return new ResultAnnouncerFacade(dateTimeDrawResult, resultAnnouncerRepository);
    }
}
