package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.LetterFeedback;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @ParameterizedTest
    @DisplayName("starts a new game with word length 5")
    @MethodSource("provide5letterWordsWithHints")
    void startGame(String word, List<Character> hint) {
        // Given
        Game game = Game.playing(word);
        WordService mockService = mock(WordService.class);
        when(mockService.provideRandomWord(5))
                .thenReturn(new Word(word).getValue());
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.save(game))
                .thenReturn(game);
        TrainerService service = new TrainerService(mockService, mockRepository);

        // When
        Progress result = service.startGame();
        Progress expectedResult = new Progress(null,0, List.of(), hint, 1);

        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void guessWord() {
        // Given
        Game game = Game.playing("woord");
        Feedback expectedFeedback = new Feedback("woird", List.of(LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID, LetterFeedback.INVALID));
        WordService mockService = mock(WordService.class);
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenReturn(Optional.of(game));

        Progress expectedResult = new Progress(1L,0, List.of(expectedFeedback), List.of('w', '.', '.', '.', '.'), 1);
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        Progress result = service.guessWord(1L, "woird");
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void getProgress() {
        // Given
        Game game = Game.withProgress();
        WordService mockService = mock(WordService.class);
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenReturn(Optional.of(game));

        Progress expectedResult = new Progress(1L,50, List.of(),  List.of('s', '.', '.', '.', '.', '.', '.'), 3);
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        Progress result = service.getProgress(1L);
        // Then
        assertEquals(expectedResult, result);
    }

    static Stream<Arguments> provide5letterWordsWithHints() {
        return Stream.of(
                Arguments.of("aapje", List.of('a', '.', '.', '.', '.')),
                Arguments.of("baart", List.of('b', '.', '.', '.', '.')),
                Arguments.of("cupje", List.of('c', '.', '.', '.', '.'))
        );
    }

    @Test
    void startNewRound() {
        // Given
        Game game = Game.waitingForRound();
        WordService mockService = mock(WordService.class);
            when(mockService.provideRandomWord(6))
                    .thenReturn((new Word("aspect")).getValue());
        GameRepository mockRepository = mock(GameRepository.class);
                when(mockRepository.findById(1L))
                        .thenReturn(Optional.of(game));
        Progress expectedResult = new Progress(1L,15, List.of(), List.of('a', '.', '.', '.', '.', '.'), 2);
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        Progress result = service.startNewRound(1L);
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void startNewRoundWhenGameNotFound() {
        // Given
        WordService mockService = mock(WordService.class);
        GameRepository mockRepository = mock(GameRepository.class);
            when(mockRepository.findById(1L))
                    .thenThrow(new EntityNotFoundException("Game not found"));
        EntityNotFoundException expectedException = new EntityNotFoundException("Game not found");
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.startNewRound(1L)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void guessWhenGameNotFound() {
        // Given
        WordService mockService = mock(WordService.class);
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("Game not found"));
        EntityNotFoundException expectedException = new EntityNotFoundException("Game not found");
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.guessWord(1L, "woord")
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void getProgressWhenGameNotFound() {
        // Given
        WordService mockService = mock(WordService.class);
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("Game not found"));
        EntityNotFoundException expectedException = new EntityNotFoundException("Game not found");
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> service.getProgress(1L)
        );
        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void isCorrectlySpelled() {
        // Given
        String word = "woord";
        WordService mockService = mock(WordService.class);
        when(mockService.wordExists(word))
                .thenReturn(true);
        GameRepository mockRepository = mock(GameRepository.class);
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        // Then
        assertTrue(service.isCorrectlySpelled(word));
    }

    @Test
    void isInCorrectlySpelled() {
        // Given
        String word = "woooord";
        WordService mockService = mock(WordService.class);
        when(mockService.wordExists(word))
                .thenReturn(false);
        GameRepository mockRepository = mock(GameRepository.class);
        // When
        TrainerService service = new TrainerService(mockService, mockRepository);
        // Then
        assertFalse(service.isCorrectlySpelled(word));
    }
}