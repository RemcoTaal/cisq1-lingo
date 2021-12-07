package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    void guess() {
        //Given
        Round round = new Round("woord");
        String guessedWord = "woorden";
        //When
        round.guess(guessedWord);


    }

    @Test
    void guessedWordIsCorrectlySpelled() {
        // Given
        Round round = new Round("woord");
        String guessedWord = "woord";
        // When
        boolean result = round.guessedWordIsCorrectlySpelled(guessedWord);
        // Then
        assertTrue(result);
    }

    @Test
    void guessedWordIsIncorrectlySpelled() {
        //Given
        Round round = new Round("woord");
        String guessedWord = "weird";
        //When
        boolean result = round.guessedWordIsCorrectlySpelled(guessedWord);
        //Then
        assertFalse(result);
    }

    @Test
    void guessedWordIsCorrectLength() {
        //Given
        Round round = new Round("woord");
        String guessedWord = "weird";
        //When
        boolean result = round.guessedWordIsCorrectLength(guessedWord);
        //Then
        assertTrue(result);
    }

    @Test
    void guessedWordIsIncorrectLength() {
        //Given
        Round round = new Round("woord");
        String guessedWord = "woorden";
        //When
        boolean result = round.guessedWordIsCorrectLength(guessedWord);
        //Then
        assertFalse(result);
    }

    @Test
    void getFeedbackHistory() {
        // Given
        Round round = new Round("woord");
        Feedback feedback = Feedback.valid("waard");
        round.addFeedbackToHistory(feedback);
        // When
        List<Feedback> expectedFeedbackHistory = round.getFeedbackHistory();
        // Then
        assertEquals(expectedFeedbackHistory, List.of(feedback));

    }

    @Test
    void invalidGuess() {
        // Given
        Round round = new Round("woord");
        round.setAttempts(5);
        // When
        assertThrows(
                // Then
                InvalidAttemptException.class,
                // When
                () -> round.guess("woord")
        );
    }

    @Test
    void getCurrentWordLength() {
        // Given
        Round round = new Round("woord");
        // When
        int currentWordLength = round.getCurrentWordLength();
        // Then
        assertEquals(5, currentWordLength);
    }
}