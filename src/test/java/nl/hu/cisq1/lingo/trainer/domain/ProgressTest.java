package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProgressTest {

    private Progress progress;

    @BeforeEach
    void init() {
        this.progress = new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectScores")
    void getScore(Progress progress, int expectedScore) {
        // When
        int score = progress.getScore();
        // Then
        assertThat(expectedScore, is(score));
    }

    private static Stream<Arguments> provideCorrectScores() {
        return Stream.of(
                Arguments.of(
                        // Progress
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),                        // Object
                        0
                ),
                Arguments.of(
                        // Progress
                        new Progress(1, 1, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),                        // Object
                        1
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideWrongScores")
    void getWrongScore(Progress progress, int wrongScore) {
        // When
        int score = progress.getScore();
        // Then
        assertThat(score, not(wrongScore));
    }

    private static Stream<Arguments> provideWrongScores() {
        return Stream.of(
                Arguments.of(
                        // Progress
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),                        // Object
                        1
                ),
                Arguments.of(
                        // Progress
                        new Progress(1,1, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),                   // Object
                        2
                )
        );
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
        Progress progress = new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1);
        // Then
        assertThat(progress, is(this.progress));
        assertThat(this.progress, is(this.progress));
    }

    @ParameterizedTest
    @MethodSource("provideNotEqualObjects")
    void testNotEquals(Progress progress, Object object) {
        // When
        assertThat(progress, not(object));
    }

    private static Stream<Arguments> provideNotEqualObjects() {
        return Stream.of(
                Arguments.of(
                        // Progress
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object
                        null
                ),
                Arguments.of(
                        // Progress
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object
                        new Game()
                ),
                Arguments.of(
                        // Progress
                        new Progress(1, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object different gameId
                        new Progress(2, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1)
                ),
                Arguments.of(
                        // Progress
                        new Progress(1, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object different score
                        new Progress(1, 5, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1)
                ),
                Arguments.of(
                        // Progress
                        new Progress(1, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object different lastFeedback
                        new Progress(1, 0, List.of(LetterFeedback.ABSENT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1)
                ),
                Arguments.of(
                        // Progress
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object different round number
                        new Progress(1,0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 3)
                ),
                Arguments.of(
                        // Progress
                        new Progress(1, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't', 'i', 'n', 'g'), 1),
                        // Object different current hint
                        new Progress(1, 0, List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT), List.of('t', 'e', 's', 't'), 1)
                )
        );
    }



    @Test
    void testHashCode() {
        // Given
        int gameId = 1;
        int score = 0;
        List<Character> hint = List.of('t', 'e', 's', 't', 'i', 'n', 'g');
        List<LetterFeedback> latestLetterFeedback = List.of(LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT);
        int roundNumber = 1;
        int expectedResult = Objects.hash(gameId, score, latestLetterFeedback, hint, roundNumber);
        // When
        int result = this.progress.hashCode();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void testToString() {
        // Given
        String expectedResult = "Progress{gameId=1, score=0, latestLetterFeedback=[CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT], currentHint=[t, e, s, t, i, n, g], roundNumber=1}";
        // When
        String result = this.progress.toString();
        // Then
        assertEquals(expectedResult, result);
    }
}