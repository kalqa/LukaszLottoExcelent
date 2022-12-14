package pl.lotto.numberreceiver;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static pl.lotto.numberreceiver.NumbersReceiverMessageProvider.*;

@Service
class NumbersReceiverValidator {

    List<String> messages = new LinkedList<>();

    boolean validate(Set<Integer> inputNumbers) {
        if (isEqualsSixNumbers(inputNumbers)) {
            messages.add(EQUALS_SIX_NUMBERS);
        } else if (isLessThanSixNumbers(inputNumbers)) {
            messages.add(LESS_THAN_SIX_NUMBERS);
        } else if (isMoreThanSixNumbers(inputNumbers)) {
            messages.add(MORE_THAN_SIX_NUMBERS);
        } else if (isNotInRangeNumbers(inputNumbers)) {
            messages.add(NOT_IN_RANGE_NUMBERS);
        } else if (isEmptyNumbers(inputNumbers)) {
            messages.add(NUMBERS_IS_EMPTY);
        }
        return true;
    }

    boolean isEmptyNumbers(Set<Integer> inputNumbers) {
        return inputNumbers.isEmpty();
    }

    boolean isLessThanSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() < SIZE_MAX;
    }

    boolean isEqualsSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() == SIZE_MAX;
    }

    boolean isNotInRangeNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.stream()
                .anyMatch(validNumbers -> numberIsMoreThan99AndLessThan1(inputNumbers));
    }

    boolean numberIsMoreThan99AndLessThan1(Collection<Integer> inputNumbers) {
        return inputNumbers.stream().findAny().orElse(0) > RANGE_TO_NUMBER &&
                inputNumbers.stream().findAny().orElse(0) < RANGE_FROM_NUMBER;
    }

    boolean isMoreThanSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() > SIZE_MAX;
    }
}
