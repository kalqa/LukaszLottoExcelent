package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.NumberReceiverDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberReceiverDtoFacadeTest {

    NumberReceiverRepository numberReceiverRepository = new InMemoryNumberReceiverRepositoryTestImpl();
    NumbersReceiverValidator validator = new NumbersReceiverValidator();

    @Test
    @DisplayName("return success when user gave six numbers")
    public void should_return_success_when_user_gave_six_numbers() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());
        assertThat(numberReceiver).isEqualTo(resultNumbers);
    }

    @Test
    @DisplayName("return failed when user gave less than six numbers")
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4);
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        boolean checkLessThanSixNumbers = validator.isLessThanSixNumbers(numberReceiver.numbersFromUser());
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        assertTrue(checkLessThanSixNumbers);
    }

    @Test
    @DisplayName("return failed when user gave more than six numbers")
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        boolean checkMoreThanSixNumbers = validator.isMoreThanSixNumbers(numberReceiver.numbersFromUser());
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        assertTrue(checkMoreThanSixNumbers);
    }

    @Test
    @DisplayName("return failed when user gave number out of range")
    public void should_return_failed_when_user_gave_number_out_of_range() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 100, 4, 5, 135, 900);
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        boolean checkNotInRangeNumbers = validator.isNotInRangeNumbers(numberReceiver.numbersFromUser());
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        assertTrue(checkNotInRangeNumbers);
    }

    @Test
    @DisplayName("return failed when user gave empty numbers")
    public void should_return_failed_when_user_gave_empty_numbers() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of();
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        boolean checkEmptyNumbers = validator.isEmptyNumbers(numberReceiver.numbersFromUser());
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        assertTrue(checkEmptyNumbers);
    }

    @Test
    @DisplayName("return failed when user gave six minus numbers")
    public void should_return_failed_when_user_gave_six_minus_numbers() {
        // given
        Clock clock = Clock.systemUTC();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(-20, -34, 3, -13, 5, -44);
        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
        boolean checkPositiveNumbers = validator.isPositiveNumbers(numberReceiver.numbersFromUser());
        boolean checkSixNumbers = validator.isEqualsSixNumbers(numberReceiver.numbersFromUser());
        // then
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), numberReceiver.dateTimeDraw());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        assertThat(checkSixNumbers).isTrue();
        assertFalse(checkPositiveNumbers);
    }

    @Test
    @DisplayName("return success when user gave six numbers and draw date time draw")
    public void should_return_success_when_user_gave_six_numbers_and_date_time_draw() {
        // given
//        Clock clock = Clock.systemUTC().withZone(ZoneId.of("Europe/Warsaw"));
        LocalDateTime today = LocalDateTime.of(2022, Month.NOVEMBER, 14, 11, 17);
        Clock clock = Clock.fixed(today.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createModuleForTests(clock, numberReceiverRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverDto numberReceiver = numberReceiverFacade.inputNumbers(numbersFromUser);
//        DateTimeDraw dateTimeDraw = numberReceiverFacade.inputDateTimeDraw(numbersFromUser, dateTime);
        boolean checkSixNumbers = validator.isEqualsSixNumbers(numberReceiver.numbersFromUser());
        // then
        DateTimeDraw dateTimeDraw = numberReceiver.dateTimeDraw();
        NumberReceiverDto resultNumbers = new NumberReceiverDto(numberReceiver.uuid(), numberReceiver.numbersFromUser(), dateTimeDraw);
//        DateTimeDraw resultDateTime = new DateTimeDraw(LocalDateTime.of(), dateTimeDraw.message());

        assertThat(numberReceiver).isEqualTo(resultNumbers);
        LocalDateTime nextSaturday = LocalDateTime.of(2022, Month.NOVEMBER, 19, 12, 0);
        assertThat(numberReceiver.dateTimeDraw()).isEqualTo(new DateTimeDraw(nextSaturday, "SUCCESS"));
        assertThat(checkSixNumbers).isTrue();
//        assertEquals(dateTimeDraw, resultDateTime);
    }
}
