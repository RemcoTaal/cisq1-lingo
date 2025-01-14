package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.RoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Round round;

    @Test
    @DisplayName("start a new round when")
    void startNewRound() {
        // Given
        Game game = new Game();
        Round expectedRound = new Round("woord");
        // When
        game.startNewRound("woord");
        // Then
        assertEquals(expectedRound, game.currentRound);
    }

    @Test
    @DisplayName("try to start new round when there is an active round")
    void startNewRoundWhenActiveRound() {
        // Given
        Game game = Game.playing("woord");
        // When Then
        assertThrows(
                RoundException.class,
                () -> game.startNewRound("woord"));
    }

    @Test
    @DisplayName("try to start new round when eliminated")
    void startNewRoundWhenEliminated() {
        // Given
        Game game = Game.eliminated();
        RoundException expectedException = RoundException.playerEliminated();
        // When
        RoundException exception = assertThrows(
                RoundException.class,
                () -> game.startNewRound("woord")
        );
        // Then
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void guessWhenAttemptLimitExceeded() {
        // Given
        Game game = Game.playing("woord");
        game.guess("waagt", true);
        game.guess("waait", true);
        game.guess("water", true);
        game.guess("waakt", true);
        game.guess("wegen", true);
        GameStatus expectedResult = GameStatus.ELIMINATED;
        // When
        game.guess("weten", true);
        GameStatus result = game.status;
        assertEquals(expectedResult, result);
    }

    @Test
    void guessWhenEliminated() {
        // Given
        Game game = Game.eliminated();
        // When Then
        assertThrows(
                InvalidAttemptException.class,
                () -> game.guess("woord", true)
        );
    }

    @Test
    void guessWhenWaitingForRound() {
        // Given
        Game game = Game.waitingForRound();
        // When Then
        assertThrows(
                InvalidAttemptException.class,
                () -> game.guess("whatever", true));
    }

    @Test
    void guessIncorrectlySpelledWord() {
        // Given
        Game game = Game.playing("woord");
        int expectedAttempts = 1;
        List<Feedback> expectedFeedbackHistory = List.of(new Feedback("wooord", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID)));
        // When
        game.guess("wooord", false);
        // Then
        assertEquals(expectedAttempts, game.currentRound.getAttempts());
        assertEquals(expectedFeedbackHistory, game.currentRound.getFeedbackHistory());
    }

    @Test
    void addRoundToHistory() {
        // Given
        Game game = new Game();
        Round expectedRound = new Round("woord");
        // When
        game.addRound(expectedRound);
        // Then
        assertEquals(List.of(expectedRound), game.getRounds());
    }

    @Test
    void showProgress() {
        // Given
        Game game = Game.playing("woord");
        game.guess("weten", true);
        game.guess("wreed", true);
        Progress expectedProgress = new Progress(
                1L,
                GameStatus.PLAYING,
                0,
                List.of(
                        new Feedback("weten",
                                List.of(
                                        LetterFeedback.CORRECT,
                                        LetterFeedback.ABSENT,
                                        LetterFeedback.ABSENT,
                                        LetterFeedback.ABSENT,
                                        LetterFeedback.ABSENT
                                )
                        ),
                        new Feedback("wreed",
                                List.of(
                                        LetterFeedback.CORRECT,
                                        LetterFeedback.PRESENT,
                                        LetterFeedback.ABSENT,
                                        LetterFeedback.ABSENT,
                                        LetterFeedback.CORRECT
                                ))),
                List.of('w', '.', '.', '.', 'd'), 1);
        // When
        Progress progress = game.showProgress();
        // Then
        assertEquals(expectedProgress, progress);
    }

    @Test
    void playerIsEliminated() {
        // Given
        Game game = Game.eliminated();
        // Then
        assertTrue(game.isPlayerEliminated());
    }

    @Test
    void playerIsNotEliminated() {
        // Given
        Game game = new Game();
        // When Then
        assertFalse(game.isPlayerEliminated());
    }

    @Test
    void isPlaying() {
        // Given
        Game game = Game.playing("woord");
        // Then
        assertTrue(game.isPlaying());
    }

    @Test
    void isNotPLaying() {
        // Given
        Game game = new Game();
        // When Then
        assertFalse(game.isPlaying());
    }

    @Test
    void provideNextWordLength() {
        // Given
        Game game = Game.playing("woord");
        // When
        int nextWordLength = game.provideNextWordLength();
        // Then
        assertEquals(6, nextWordLength);
    }

    @Test
    void getRoundNr() {
        // Given
        Game game = Game.playing("woord");
        // When
        int roundNr = game.getRoundNr();
        // Then
        assertEquals(1, roundNr);
    }

    @Test
    void calculateScore() {
        // Given
        Game game = new Game();
        Round round1 = new Round("woord");
        round1.guess("waard", true);
        round1.guess("woord", true);
        game.addRound(round1);
        Round round2 = new Round("anders");
        round2.guess("actief", true);
        round2.guess("aanzet", true);
        round2.guess("afloop", true);
        round2.guess("anders", true);
        game.addRound(round2);
        // When
        int score = game.calculateScore();
        // Then
        assertEquals(30, score);
    }
}