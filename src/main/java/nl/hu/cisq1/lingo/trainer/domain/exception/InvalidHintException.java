package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.LetterFeedback;

import java.text.MessageFormat;
import java.util.List;

public class InvalidHintException extends RuntimeException {
    public InvalidHintException(String message) {
        super(message);
    }

    public static InvalidHintException wrongLetterFeedbackListAndWordToGuessLength(List<LetterFeedback> letterFeedbackList, String wordToGuess) {
        return new InvalidHintException(MessageFormat.format("letterfeedback list length: {0} is not the same as wordToGuess length: {1}", letterFeedbackList.size(), wordToGuess.length()));
    }

    public static InvalidHintException wrongPreviousHintAndLetterFeedbackListLength(List<Character> previousHint, List<LetterFeedback> letterFeedbackList) {
        return new InvalidHintException(MessageFormat.format("previous hint length: {0} is not the same as letterfeedback list length: {1}", previousHint.size(), letterFeedbackList.size()));
    }
}
