package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed() {
        // When
        Feedback feedback = new Feedback("woord", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // Then
        assertTrue(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("word is not guessed if not all the letters are correct")
    void wordIsNotGuessed() {
        // When
        Feedback feedback = new Feedback("weird", List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // Then
        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("guess is valid if none of the letters is invalid")
    void guessIsValid() {
        // When
        Feedback feedback = new Feedback("weird", List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // Then
        assertTrue(feedback.isGuessValid());
    }

    @Test
    @DisplayName("guess is invalid if one of the letters is invalid")
    void guessIsInvalid() {
        // When
        Feedback feedback = new Feedback("woordt", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID));
        // Then
        assertFalse(feedback.isGuessValid());
    }

    @Test
    @DisplayName("feedback is correct if length of attempt is equals to size of letterfeedback list")
    void isValid() {
        // Given
        Feedback validFeedback = new Feedback("woord", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // When
        Feedback feedback = Feedback.valid("woord");
        // Then
        assertEquals(validFeedback, feedback);
    }

    @Test
    @DisplayName("feedback is invalid if length of attempt is not equals to size of letterfeedback list")
    void isInvalid() {
        assertThrows(
                // Then
                InvalidFeedbackException.class,
                // When
                () -> Feedback.invalid("woord")
        );
    }

    @ParameterizedTest
    @MethodSource("provideHintsForGiveHint")
    @DisplayName("provide a hint based on the previous hint, guessedWord and word to guess")
    void giveHint(String attempt, List<LetterFeedback> letterFeedbackList, List<Character> previousHint, List<Character> expectedHint) {
        // Given
        Feedback feedback = new Feedback(attempt, letterFeedbackList);
        // When
        List<Character> hint = feedback.giveHint(previousHint, "woord");
        // Then
        assertEquals(expectedHint, hint);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidHintsForGiveHint")
    @DisplayName("provide a invalid hint where the previous hint does not match the length of the feedback list ")
    void previousHintAndFeedbackListNotSameLength(List<LetterFeedback> letterFeedbackList, List<Character> previousHint) {
        // Given
        Feedback feedback = new Feedback("waard", letterFeedbackList);
        // Then
        assertThrows(
                InvalidHintException.class,
                () -> feedback.giveHint(previousHint, "woord")
        );
    }

    @ParameterizedTest
    @MethodSource("provideLetterFeedbackAndWordToGuess")
    @DisplayName("provide feedback list and word to guess that does not match in length ")
    void wordToGuessAndFeedbackListNotSameLength(String wordToGuess, List<LetterFeedback> letterFeedbackList) {
        // Given
        Feedback feedback = new Feedback("waarde", letterFeedbackList);
        // Then
        assertThrows(
                InvalidHintException.class,
                () -> feedback.giveHint(List.of('w', '.', '.', '.', '.', '.'), wordToGuess)
        );
    }

    private static Stream<Arguments> provideInvalidHintsForGiveHint() {
        return Stream.of(
                Arguments.of(
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('w', '.', '.', '.')),
                Arguments.of(
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('w', '.', '.', '.', '.', '.')),
                Arguments.of(
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('.', '.', 'r', 'd')),
                Arguments.of(
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('w', '.', '.', 'r', 'd', 't'))
                );
    }

    private static Stream<Arguments> provideHintsForGiveHint() {
        return Stream.of(
                Arguments.of(
                        // Guessed word
                        "weide",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.PRESENT, LetterFeedback.ABSENT),
                        // Previous hint
                        List.of('w', '.', '.', '.', '.'),
                        // Expected hint
                        List.of('w', '.', '.', '.', '.')),
                Arguments.of(
                        // Guessed word
                        "waard",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('w', '.', '.', '.', '.'),
                        // Expected hint
                        List.of('w', '.', '.', 'r', 'd')),
                Arguments.of(
                        // Guessed word
                        "weerd",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT),
                        // Previous hint
                        List.of('w', '.', '.', 'r', 'd'),
                        // Expected hint
                        List.of('w', '.', '.', 'r', 'd')),
                Arguments.of(
                        // Guessed word
                        "wokte",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT),
                        // Previous hint
                        List.of('w', '.', '.', 'r', 'd'),
                        // Expected hint
                        List.of('w', 'o', '.', 'r', 'd'))
        );

    }

    private static Stream<Arguments> provideLetterFeedbackAndWordToGuess() {
        return Stream.of(
                Arguments.of(
                        // Guessed word
                        "woord",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.PRESENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT)
                ),
                Arguments.of(
                        // Guessed word
                        "waard",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.ABSENT)
                ),
                Arguments.of(
                        // Guessed word
                        "weerd",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.ABSENT)
                ),
                Arguments.of(
                        // Guessed word
                        "wokte",
                        // Feedback
                        List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT, LetterFeedback.ABSENT)
                )
        );
    }

    @Test
    void testEquals() {
        // Given
        Feedback feedback1 = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        Feedback feedback2 = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // When
        boolean result = feedback1.equals(feedback2);
        // Then
        assertTrue(result);
    }

    @Test
    void testNotEquals() {
        // Given
        Feedback feedback1 = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        Feedback feedback2 = new Feedback("testing", List.of(LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // When
        boolean result = feedback1.equals(feedback2);
        // Then
        assertFalse(result);
    }

    @Test
    void testHashCode() {
        // Given
        String attempt = "testing";
        List<LetterFeedback> letterFeedbackList = List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT);
        Feedback feedback = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        int expectedResult = Objects.hash(attempt, letterFeedbackList);
        // When
        int result = feedback.hashCode();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void testToString() {
        // Given
        Feedback feedback = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        String expectedResult = "Feedback{attempt='testing', letterFeedbackList=[CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT]}";
        // When
        String result = feedback.toString();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void getLetterFeedbackList() {
        // Given
        Feedback feedback = new Feedback("testing", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        List<LetterFeedback> expectedResult = List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT);
        // When
        List<LetterFeedback> result = feedback.getLetterFeedbackList();
        // Then
        assertEquals(expectedResult, result);
    }
}