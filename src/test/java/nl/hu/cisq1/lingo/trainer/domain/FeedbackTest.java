package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("word is guessed if all letters are correct")
    void wordIsGuessed(){
        // When
        Feedback feedback = new Feedback("woord", List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT));
        // Then
        assertTrue(feedback.isWordGuessed());

    }

    @Test
    @DisplayName("word is not guessed if not all the letters are correct")
    void wordIsNotGuessed(){
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
        assertTrue(feedback.isValid());
    }

    @Test
    @DisplayName("guess is invalid if one of the letters is invalid")
    void guessIsInvalid() {
        // When
        Feedback feedback = new Feedback("woordt", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID));
        // Then
        assertFalse(feedback.isValid());
    }
}