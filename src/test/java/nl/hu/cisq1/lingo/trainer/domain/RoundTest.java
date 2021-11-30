package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    void newAttempt() {
        //Given
        Round round = new Round("woord");
        String guessedWord = "woorden";
        //When
        round.newAttempt(guessedWord);


    }

    @Test
    void guessedWordIsCorrectlySpelled() {
        Round round = new Round("woord");
        String guessedWord = "woord";

        boolean result = round.guessedWordIsCorrectlySpelled(guessedWord);

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
}