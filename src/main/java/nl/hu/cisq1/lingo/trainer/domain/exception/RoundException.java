package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundException extends RuntimeException {
    public RoundException(String message) {
        super(message);
    }

    public static RoundException activeRound() {
        return new RoundException("There is still an active round, so you cannot start a new one");
    }

    public static RoundException playerEliminated() {
        return new RoundException("You cannot start a new round when eliminated, start a new game instead");
    }
}
