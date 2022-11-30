package pl.lotto.numbersgenerator;

import lombok.extern.log4j.Log4j;
import pl.lotto.numbersgenerator.dto.WinningNumbersDto;
import pl.lotto.resultchecker.ResultsCheckerFacade;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.lotto.numbersgenerator.WinningNumbersMessageProvider.winningNumbersNotFound;

@Log4j
class NumbersGeneratorFacade {

    private ResultsCheckerFacade resultsCheckerFacade;
    private static final Integer MIN_NUMBER = 1;
    private static final Integer MAX_NUMBER = 99;

    NumbersGeneratorFacade(ResultsCheckerFacade resultsCheckerFacade) {
        this.resultsCheckerFacade = resultsCheckerFacade;
    }

    Set<Integer> generateLottoNumbers() {
        return IntStream.rangeClosed(MIN_NUMBER, MAX_NUMBER)
                .map(randomNumbers -> new Random().nextInt())
                .boxed()
                .collect(Collectors.toSet());
    }

    WinningNumbersDto showWinnerNumbers() {
        Set<Integer> lottoNumbers = generateLottoNumbers();
        for (Integer lottoNumber : lottoNumbers) {
            return new WinningNumbersDto(Set.of(lottoNumber));
        }
        winningNumbersNotFound();
        return new WinningNumbersDto(Set.of(0));
    }
}