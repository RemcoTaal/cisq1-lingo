package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.text.MessageFormat;

public class InvalidAttemptException extends RuntimeException {
    public InvalidAttemptException(String message) {
        super(message);
    }

    public static InvalidAttemptException limitExceeded(Round round) {
        return new InvalidAttemptException(MessageFormat.format("The attempt limit of 5 has been exceeded, current attempt: {0}", round.getAttempts()));
    }

    public static InvalidAttemptException playerEliminated(){
        return new InvalidAttemptException("The attempt is invalid because the player is eliminated");
    }

    public static InvalidAttemptException noActiveRound(){
        return new InvalidAttemptException("There is no active round, so you cannot guess when there is no round active");
    }
}
