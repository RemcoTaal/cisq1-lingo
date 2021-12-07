package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Round round;

    @BeforeEach
    void init(){
        // Given
        this.game = new Game();
        this.round = new Round("woord");
    }

    @Test
    void startNewRound() {
        // When
        game.startNewRound("woord");
        // Then
        assertEquals(round, game.currentRound);
    }

    @Test
    void guess() {
    }

    @Test
    void addRoundToHistory() {
        // When
        game.addRoundToHistory(round);
        // Then
        assertEquals(List.of(round), game.getRoundHistory());
    }

    @Test
    void showProgress() {
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
        // Then
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
        // Then
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
}