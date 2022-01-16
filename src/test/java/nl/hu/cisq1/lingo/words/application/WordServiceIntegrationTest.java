package nl.hu.cisq1.lingo.words.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This integration test integrates between the service layer,
 * the data layer and the framework.
 * In a dev environment, we test against the actual database.
 *
 * In continuous integration pipelines, we should not
 * use the actual database as we don't have one.
 * We want to replace it with an in-memory database.
 *
 * Set the profile to CI, so that application-ci.properties is loaded
 * and an import script is run.
 **/
@SpringBootTest
@Import(CiTestConfiguration.class)
class WordServiceIntegrationTest {

    @Autowired
    private WordService service;

    @Test
    @DisplayName("provides random 5, 6 and 7 letter words")
    void providesRandomWord() {
        for (int wordLength = 5; wordLength <= 7; wordLength++) {
            String randomWord = this.service.provideRandomWord(wordLength);
            assertEquals(wordLength, randomWord.length());

            // Printing is not necessary in most tests
            // (done here for verification of student configuration)
            System.out.println("Random word: " + randomWord);
        }
    }

    @Test
    @DisplayName("check if a word exists in the wordrepository")
    void wordExists() {
        String word = "pizza";
        assertTrue(this.service.wordExists(word));
    }

    @Test
    @DisplayName("check if the word does not exist in the wordrepository")
    void wordDoesNotExist() {
        String word = "weird";
        assertFalse(this.service.wordExists(word));
    }
}
