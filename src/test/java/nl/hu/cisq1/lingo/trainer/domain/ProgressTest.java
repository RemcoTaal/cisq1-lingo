package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTest {

    private Progress progress;

    @BeforeEach
    void init() {
        this.progress = new Progress(0, List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1);
    }

    @Test
    void getScore() {
        // Given
        int expectedResult = 0;
        // When
        int result = this.progress.getScore();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void getCurrentHint() {
        // Given
        List<Character> expectedResult = List.of('t', 'e', 's', 't', 'i', 'n', 'g');
        // When
        List<Character> result = this.progress.getCurrentHint();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void getRoundNumber() {
        // Given
        int expectedResult = 1;
        // When
        int result = this.progress.getRoundNumber();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void testEquals() {
        // Given
        Progress progress = new Progress(0, List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1);
        // Then
        assertThat(progress, equalTo(this.progress));
    }

    @Test
    void testEqualReference() {
        //When
        assertThat(this.progress, equalTo(this.progress));
    }

    @Test
    void testNotEqualReference() {
        // Given
        Progress progress = new Progress(0, List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1);
        boolean expectedResult = this.progress == progress;
        // When then
        assertFalse(expectedResult);
    }

    @Test
    void testNotEqualsClass() {
        // Given
        Game game = new Game();
        // When
        assertThat(this.progress, not(game));
    }

    @Test
    void testHashCode() {
        // Given
        int score = 0;
        List<Character> hint = List.of('t', 'e', 's', 't', 'i', 'n', 'g');
        int roundNumber = 1;
        int expectedResult = Objects.hash(score, hint, roundNumber);
        // When
        int result = this.progress.hashCode();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void testToString() {
        // Given
        String expectedResult = "Progress{score=0, currentHint=[t, e, s, t, i, n, g], roundNumber=1}";
        // When
        String result = this.progress.toString();
        // Then
        assertEquals(expectedResult, result);
    }
}