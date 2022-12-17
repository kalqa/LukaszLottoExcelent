package pl.lotto.resultannouncer;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.temporal.TemporalAdjusters.next;

@Service
class DateTimeDrawResult {

    private final Clock clock;

    DateTimeDrawResult(Clock clock) {
        this.clock = clock;
    }

    LocalDateTime generateNextDrawDate() {
        return LocalDateTime.now(clock)
                .with(next(SATURDAY))
                .withHour(12)
                .withMinute(0);
    }
}
