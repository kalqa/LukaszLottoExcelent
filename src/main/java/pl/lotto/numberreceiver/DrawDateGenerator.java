//package pl.lotto.numberreceiver;
//
//import java.time.LocalDateTime;
//import static java.time.DayOfWeek.SATURDAY;
//import static java.time.temporal.TemporalAdjusters.next;
//
//public class DrawDateGenerator {
//
//    LocalDateTime generateNextDrawDate() {
//        return LocalDateTime.now(clock)
//                .with(next(SATURDAY))
//                .withHour(12)
//                .withMinute(0);
//    }
//}
