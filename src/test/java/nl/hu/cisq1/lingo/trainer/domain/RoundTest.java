package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    void validGuess() {
        // Given
        Round round = new Round("woord");
        String guessedWord = "waard";
        Feedback feedback = new Feedback(guessedWord, List.of(
                LetterFeedback.CORRECT,
                LetterFeedback.ABSENT,
                LetterFeedback.ABSENT,
                LetterFeedback.CORRECT,
                LetterFeedback.CORRECT)
        );
        // When
        round.guess(guessedWord);
        // Then
        assertEquals(round.getFeedbackHistory(), List.of(feedback));
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

    @Disabled
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

    @Disabled
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
    void getCurrentWordLength() {
        // Given
        Round round = new Round("woord");
        // When
        int currentWordLength = round.getCurrentWordLength();
        // Then
        assertEquals(5, currentWordLength);
    }

    @ParameterizedTest
    @MethodSource("provideWordsWithFeedback")
    @DisplayName("provide wordToGuess, guessedWord and Letterfeedback list for checkCharPositions function")
    void checkCharPositions(String wordToGuess, String guessedWord, List<LetterFeedback> exceptedLetterFeedbackList) {
        // Given
        Round round = new Round(wordToGuess);
        // When
        List<LetterFeedback> actualLetterFeedbackList = round.checkCharPositions(guessedWord);
        // Then
        assertEquals(exceptedLetterFeedbackList, actualLetterFeedbackList
        );
    }

    private static Stream<Arguments> provideWordsWithFeedback() {
        return Stream.of(
                Arguments.of(
                        // Word to guess
                        "woord",
                        // Guessed word
                        "waard",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT)),
                Arguments.of(
                        // Word to guess
                        "aanbod",
                        // Guessed word
                        "bekend",
                        // Feedback
                        List.of(LetterFeedback.PRESENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.PRESENT, LetterFeedback.CORRECT)),
                Arguments.of(
                        // Word to guess
                        "afgrond",
                        // Guessed word
                        "dronken",
                        // Feedback
                        List.of(LetterFeedback.PRESENT, LetterFeedback.PRESENT, LetterFeedback.PRESENT, LetterFeedback.PRESENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.PRESENT))
                );
    }

    @Test
    void giveFeedback() {
        // Given
        Round round = new Round("woord");
        Feedback expectedResult = new Feedback("woord", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // When
        Feedback result = round.giveFeedback("woord");
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void giveFeedbackGuessedWordIncorrectLength() {
        // Given
        Round round = new Round("woord");
        Feedback expectedResult = new Feedback("woorde", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID));
        // When
        Feedback result = round.giveFeedback("woorde");
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void giveFeedbackInvalidWord() {
        // Given
        Round round = new Round("woord");
        Feedback expectedResult = new Feedback("woorde", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID));
        // When
        Feedback result = round.giveFeedbackInvalidWord("woorde");
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void testEquals() {
        // Given
        Round round1 = new Round("woord");
        Round round2 = new Round("woord");
        // When
        boolean result = round1.equals(round2);
        // Then
        assertTrue(result);
    }

    @Test
    void testNotEquals() {
        // Given
        Round round1 = new Round("woord");
        Round round2 = new Round("waard");
        // When
        boolean result = round1.equals(round2);
        // Then
        assertFalse(result);
    }

    @Test
    void testHashCode() {
        // Given
        String wordToGuess = "woord";
        List<Feedback> feedbackHistory = new ArrayList<>();
        int attempts = 0;
        Round round = new Round(wordToGuess);
        int expectedResult = Objects.hash(wordToGuess, feedbackHistory, attempts);
        // When
        int result = round.hashCode();
        // Then
        assertEquals(expectedResult, result);
    }
}