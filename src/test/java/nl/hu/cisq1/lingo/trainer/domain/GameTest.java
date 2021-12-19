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
        game.addRound(round);
        // Then
        assertEquals(List.of(round), game.getRounds());
    }

    @Test
    void showProgress() {
        // Given
        Game game = Game.playing("woord");
        game.guess("weten");
        game.guess("wreed");
        Progress expectedProgress = new Progress(0, List.of('w', '.', '.', '.', 'd'), 1);
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
        Round round1 = new Round("woord");
        round1.guess("waard");
        round1.guess("woord");
        game.addRound(round1);
        Round round2 = new Round("anders");
        round2.guess("actief");
        round2.guess("aanzet");
        round2.guess("afloop");
        round2.guess("anders");
        game.addRound(round2);
        // When
        int score = game.calculateScore();
        // Then
        assertEquals(30, score);
    }
}