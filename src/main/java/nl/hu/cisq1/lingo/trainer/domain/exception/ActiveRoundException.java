package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.Round;

import java.text.MessageFormat;

public class ActiveRoundException extends RuntimeException {
    public ActiveRoundException(String message) {
        super(message);
    }

    public static ActiveRoundException activeRound() {
        return new ActiveRoundException("There is still an active round, so you cannot start a new one");
    }
}
