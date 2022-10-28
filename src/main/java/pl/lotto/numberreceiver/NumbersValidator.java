package pl.lotto.numberreceiver;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static pl.lotto.numberreceiver.NumbersMessageProvider.*;

class NumbersValidator {

    List<String> messagesValidation = new LinkedList<>();

    public boolean validate(Set<Integer> inputNumbers) {
        if (isEqualsSixNumbers(inputNumbers)) {
            messagesValidation.add(EQUALS_SIX_NUMBERS);
        }
        else if (isLessThanSixNumbers(inputNumbers)) {
            messagesValidation.add(LESS_THAN_SIX_NUMBERS);
        }
       else if (isMoreThanSixNumbers(inputNumbers)) {
            messagesValidation.add(MORE_THAN_SIX_NUMBERS);
        }
       else if (!isInRangeNumbers()) {
            messagesValidation.add(NOT_IN_RANGE_NUMBERS);
        }
        else if(inputNumbers.isEmpty()){
            messagesValidation.add(NUMBERS_IS_EMPTY);
        }
        return false;
    }

    boolean isLessThanSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() < SIZE_NUMBERS;
    }

    boolean isEqualsSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() == SIZE_NUMBERS;
    }

    boolean isInRangeNumbers() {
        return IntStream.rangeClosed(RANGE_FROM_NUMBER, RANGE_TO_NUMBER)
                .findAny()
                .isPresent();
    }

    boolean isMoreThanSixNumbers(Collection<Integer> inputNumbers) {
        return inputNumbers.size() > SIZE_NUMBERS;
    }
}
