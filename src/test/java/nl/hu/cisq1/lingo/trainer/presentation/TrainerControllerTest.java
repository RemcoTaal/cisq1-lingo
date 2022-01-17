package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerControllerTest {

    @Test
    void startGame() {
        // Given
        Progress expectedResult = new Progress(1L,0, null, List.of('w', '.', '.', '.', '.'), 1);
        TrainerService mockService = mock(TrainerService.class);
                when(mockService.startGame())
                        .thenReturn(expectedResult);
        // When
        TrainerController controller = new TrainerController(mockService);
        Progress result = controller.startGame();
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void startNewRound() {
        // Given
        Progress expectedResult = new Progress(1L,15, null, List.of('s', '.', '.', '.', '.', '.'), 2);
        TrainerService mockService = mock(TrainerService.class);
                when(mockService.startNewRound(1L))
                        .thenReturn(expectedResult);
        // When
        TrainerController controller = new TrainerController(mockService);
        Progress result = controller.startNewRound(1L);
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void startNewRoundWhenGameNotFound() {
        // Given
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.startNewRound(1000L))
                .thenThrow(EntityNotFoundException.class);
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // When
        TrainerController controller = new TrainerController(mockService);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.startNewRound(1000L)
        );
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    void startNewRoundWhenActiveRound() {
        // Given
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.startNewRound(1L))
                .thenThrow(RoundException.class);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        // When
        TrainerController controller = new TrainerController(mockService);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.startNewRound(1L)
        );
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    void startNewRoundWhenPlayerEliminated() {
        // Given
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.startNewRound(1L))
                .thenThrow(RoundException.class);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        // When
        TrainerController controller = new TrainerController(mockService);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.startNewRound(1L)
        );
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    void guessValid() {
        // Given
        Progress expectedResult = new Progress(1L,15, null, List.of('s', '.', '.', '.', '.', '.'), 2);
        String guessedWord = "woord";
        Map<String, String> requestBody = Map.of("guessedWord", guessedWord);
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.guessWord(1L, guessedWord))
                .thenReturn(expectedResult);
        // When
        TrainerController controller = new TrainerController(mockService);
        Progress result = controller.guess(1L, requestBody);
        assertEquals(expectedResult, result);
    }

    @Test
    void guessInvalidAttempt() throws JSONException {
        // Given
        String guessedWord = "woord";
        Map<String, String> requestBody = Map.of("guessedWord", guessedWord);
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.guessWord(1L, guessedWord))
                .thenThrow(InvalidAttemptException.class);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        // When
        TrainerController controller = new TrainerController(mockService);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.guess(1L, requestBody)
        );
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    void guessGameNotFound() {
        // Given
        String guessedWord = "woord";
        Map<String, String> requestBody = Map.of("guessedWord", guessedWord);
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.guessWord(1000L, guessedWord))
                .thenThrow(EntityNotFoundException.class);
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // When
        TrainerController controller = new TrainerController(mockService);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.guess(1000L, requestBody)
        );
        assertEquals(expectedStatus, exception.getStatus());
    }

    @Test
    void getProgress() {
        // Given
        Progress expectedResult = new Progress(1L,15, null, List.of('s', '.', '.', '.', '.', '.'), 2);
        TrainerService mockService = mock(TrainerService.class);
        when(mockService.getProgress(1L))
                .thenReturn(expectedResult);
        // When
        TrainerController controller = new TrainerController(mockService);
        Progress result = controller.getProgress(1L);
        // Then
        assertEquals(expectedResult, result);

    }
}