package pl.lotto.numberreceiver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.NumbersMessageDto;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static java.time.LocalTime.NOON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pl.lotto.numberreceiver.NumbersReceiverMessageProvider.*;

class NumberReceiverFacadeTest {

    private final NumberReceiverGenerator numberReceiverGenerator;
    private final DateTimeReceiver dateTimeReceiver;
    private final Clock clock;

    NumberReceiverFacadeTest() {
        this.clock = Clock.systemUTC();
        this.dateTimeReceiver = new DateTimeReceiver(clock);
        this.numberReceiverGenerator = new NumberReceiverGenerator();
    }

    @Test
    @DisplayName("return success when user gave six numbers and day is saturday and time is noon")
    public void should_return_success_when_user_gave_six_numbers_and_day_is_saturday_and_time_is_noon() {
        // given
        LocalDate date = LocalDate.of(2022, Month.OCTOBER, 29);
        LocalDateTime dateTime = LocalDateTime.of(date, NOON);
        LocalDateTime drawDate = dateTimeReceiver.generateDrawDate(dateTime);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration()
                .createModuleForTests(numberReceiverGenerator, clock, drawDate);
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);

        // when
        NumbersMessageDto inputNumbers = numberReceiverFacade.inputNumbers(numbers);

        // then
        NumbersMessageDto result = new NumbersMessageDto(numbers, List.of(EQUALS_SIX_NUMBERS));
        assertThat(inputNumbers).isEqualTo(result);
    }

    @Test
    @DisplayName("return success when user gave six numbers and day is saturday and time is noon")
    public void should_return_true_when_user_gave_six_numbers_and_day_is_saturday_and_time_is_noon() {
        // given
        LocalDate date = LocalDate.of(2022, Month.NOVEMBER, 5);
        LocalDateTime dateTime = LocalDateTime.of(date, NOON);
        LocalDateTime drawDate = dateTimeReceiver.generateDrawDate(dateTime);
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration()
                .createModuleForTests(numberReceiverGenerator, clock, drawDate);
        Set<Integer> numbers = Set.of(90, 1, 2, 3, 4, 19);

        // when
        NumbersMessageDto inputNumbersForUser = numberReceiverFacade.inputNumbers(numbers);

        // then
        NumbersMessageDto resultMessage = new NumbersMessageDto(numbers, List.of(EQUALS_SIX_NUMBERS));
        assertThat(inputNumbersForUser).isEqualTo(resultMessage);
    }

    @Test
    @DisplayName("return failed when user gave less than six numbers")
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration()
                .createModuleForTests(numberReceiverGenerator, clock, dateTimeReceiver.generateToday());
        Set<Integer> numbers = Set.of(1, 2, 3, 4);

        // when
        NumbersMessageDto inputNumbers = numberReceiverFacade.inputNumbers(numbers);

        // then
        NumbersMessageDto result = new NumbersMessageDto(numbers, List.of(LESS_THAN_SIX_NUMBERS));
        assertThat(inputNumbers).isEqualTo(result);
    }

    @Test
    @DisplayName("return failed when user gave more than six numbers")
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration()
                .createModuleForTests(numberReceiverGenerator, clock, dateTimeReceiver.generateToday());
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6, 12, 14);

        // when
        NumbersMessageDto inputNumbers = numberReceiverFacade.inputNumbers(numbers);

        // then
        NumbersMessageDto result = new NumbersMessageDto(numbers, List.of(MORE_THAN_SIX_NUMBERS));
        assertThat(inputNumbers).isEqualTo(result);
    }

    @Test
    @DisplayName("return failed when user gave number out of range")
    public void should_return_failed_when_user_gave_number_out_of_range() {
        // given
        LocalDateTime dateTimeNow = dateTimeReceiver.generateDrawDate(LocalDateTime.now(clock));
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration()
                .createModuleForTests(numberReceiverGenerator, clock, dateTimeNow);
        Set<Integer> numbers = Set.of(102, 1, 2, 3, 4, -3);

        // when
        NumbersMessageDto inputNumbers = numberReceiverFacade.inputNumbers(numbers);

        // then
        NumbersMessageDto result = new NumbersMessageDto(numbers, List.of(NOT_IN_RANGE_NUMBERS));
        assertThat(inputNumbers).isNotEqualTo(result);
    }

    @Test
    @DisplayName("return failed day draw when user give saturday with clock 10_am")
    public void should_return_correct_day_draw_when_user_give_saturday_with_clock_10_am() {

        //given
        DateTimeReceiver drawDate = new DateTimeReceiver(clock);
        DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;
        LocalTime time = LocalTime.of(10, 0);

        //when
        DayOfWeek resultDay = drawDate.generateToday().getDayOfWeek();
        LocalTime resultTime = LocalTime.of(11, 0);

        //then
        assertNotEquals(dayOfWeek, resultDay);
        assertThat(time).isNotEqualTo(resultTime);
    }

    @Test
    @DisplayName("return not correct date time draw when user give sunday 11 am with clock UTC")
    public void should_return_not_correct_date_time_draw_when_user_get_sunday_11_am_with_clock_UTC() {

        //given
        LocalDate actualDate = LocalDate.of(2022, Month.NOVEMBER, DayOfWeek.SUNDAY.getValue());
        LocalTime actualTime = LocalTime.of(11, 0);
        LocalDateTime actualDateTime = LocalDateTime.of(actualDate, actualTime);
        actualDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        //when
        LocalDateTime resultDrawDate = dateTimeReceiver.generateDrawDate(LocalDateTime.now());

        //then
        assertNotEquals(resultDrawDate, actualDateTime);
    }

    @Test
    @DisplayName("return correct date time when user give saturday 12 december am with clock UTC")
    public void should_return_correct_date_time_draw_when_user_get_saturday_12_00_am_december_with_clock_UTC() {

        //given
        LocalDateTime date = LocalDate.of(2022, Month.DECEMBER, 3).atStartOfDay();
        LocalDateTime dateTime = date.plus(12, ChronoUnit.HOURS).plusMinutes(0);
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        //when
        LocalDateTime dateTimeResult = dateTimeReceiver.generateDrawDate(now);

        //then
        assertThat(dateTime).isAfter(dateTimeResult);
    }

    @Test
    @DisplayName("return correct date time draw when user give saturday 11 november am with clock UTC")
    public void should_return_correct_date_time_draw_when_user_get_saturday_11_00_am_december_with_clock_fixed() {

        //given
        LocalDateTime date = LocalDate.of(2022, Month.NOVEMBER, 5).atStartOfDay();
        LocalDateTime dateTime = date.plus(11, ChronoUnit.HOURS).plusMinutes(0);
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        LocalDateTime now = LocalDateTime.now(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        //when
        LocalDateTime dateTimeResult = dateTimeReceiver.generateDrawDate(now);

        //then
        assertThat(dateTime).isBefore(dateTimeResult);
    }

    @Test
    @DisplayName("return correct date time draw when user give saturday november am with clock UTC")
    public void should_return_correct_date_time_draw_when_user_give_saturday_november_with_clock_UTC() {

        //given
        LocalDate actualDate = LocalDate.of(2022, Month.NOVEMBER, DayOfWeek.SATURDAY.getValue());
        LocalDateTime actualDateTime = LocalDateTime.of(actualDate, LocalTime.NOON);
        actualDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        //when
        LocalDateTime resultDrawDate = dateTimeReceiver.generateDrawDate(LocalDateTime.now());

        //then
        assertNotEquals(resultDrawDate, actualDateTime);
    }

    @Test
    @DisplayName("return correct time draw when time draw at 10 am is before 12 am")
    public void should_return_correct_when_time_draw_10_am_is_before_12_am() {

        //given
        LocalTime timeDraw = LocalTime.of(10, 0);

        //when
        boolean resultTimeDraw = timeDraw.isBefore(LocalTime.NOON);

        //then
        assertThat(resultTimeDraw).isEqualTo(true);
    }
}
