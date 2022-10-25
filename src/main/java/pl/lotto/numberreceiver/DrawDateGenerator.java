package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static pl.lotto.numberreceiver.DateMessageProvider.*;

public class DrawDateGenerator {

    private final Clock clock;

    DrawDateGenerator(Clock clock) {
        this.clock = clock;
    }

    LocalDateTime generateDrawDate() {
        LocalDateTime now = LocalDateTime.now(clock);
        TemporalAdjusters.next(DayOfWeek.SATURDAY);
        return now;
//        return LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTES);
    }
}
