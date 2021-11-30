package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.LetterFeedback;

import java.text.MessageFormat;
import java.util.List;

public class InvalidFeedbackException extends RuntimeException {
    public InvalidFeedbackException(String message) {
        super(message);
    }

    public static InvalidFeedbackException wrongLength(String attempt, List<LetterFeedback> letterFeedbackList) {
        return new InvalidFeedbackException(MessageFormat.format("attempt length: {0} is not the same as letterFeedbackList length: {1}", attempt.length(), letterFeedbackList.size()));
    }
}
