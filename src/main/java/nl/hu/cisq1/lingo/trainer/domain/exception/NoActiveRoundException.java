package nl.hu.cisq1.lingo.trainer.domain.exception;

public class NoActiveRoundException extends RuntimeException {
    public NoActiveRoundException(String message) {
        super(message);
    }

    public static NoActiveRoundException noActiveRound() {
        return new NoActiveRoundException("There is no active round, so you cannot guess when there is no round active");
    }
}
