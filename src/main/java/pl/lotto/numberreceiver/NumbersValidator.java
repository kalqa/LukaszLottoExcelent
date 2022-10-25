package pl.lotto.numberreceiver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.lotto.numberreceiver.dto.NumbersResultMessageDto;
import pl.lotto.numberreceiver.dto.ValidationDto;

import java.util.Set;
import java.util.stream.IntStream;

import static pl.lotto.numberreceiver.NumbersMessageProvider.*;
import static pl.lotto.numberreceiver.enums.ValidateMessage.*;

class NumbersValidator {

    List<String> errorList = new LinkedList<>();

    public boolean validate(Set<Integer> inputNumbers) {
        if(checkLessThanSixNumbers(inputNumbers)){
            errorList.add("less than six numbers");
        }
        if(checkEqualsSixNumbers(inputNumbers)){
            errorList.add("less than six numbers");
        }

        if (errorList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkLessThanSixNumbers(Set<Integer> numbersInput) {
        return numbersInput.stream()
                .map(valid -> new ValidationDto(numbersInput, NOT_CORRECT_SIZE_NUMBERS))
                .anyMatch(this::isLessThanSixSizeNumbers);
    }

    private boolean isLessThanSixSizeNumbers(ValidationDto validator) {
        return validator.numbersFromUser().size() < SIZE_NUMBERS;
    }

    boolean checkEqualsSixNumbers(Set<Integer> inputNumbers) {
        return inputNumbers.stream().anyMatch(this::isCorrectSizeNumbers);
    }

    private boolean isCorrectSizeNumbers(Integer number) {
        return number == SIZE_NUMBERS;
    }

    boolean checkMoreThanSixNumbers(Set<Integer> inputNumbers) {
        return inputNumbers.stream().anyMatch(this::isMoreThanCorrectSize);
    }

    boolean checkNumbersInRange(Set<Integer> numbersInRange) {
        return IntStream.rangeClosed(RANGE_FROM_NUMBER, RANGE_TO_NUMBER)
                .mapToObj(rangeNumbers -> new NumbersResultMessageDto(numbersInRange, IN_RANGE_NUMBERS.name()))
                .findAny()
                .isPresent();
    }

    private boolean isMoreThanCorrectSize(Integer number) {
        return number > SIZE_NUMBERS;
    }
}
