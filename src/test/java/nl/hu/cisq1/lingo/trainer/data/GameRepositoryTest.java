package nl.hu.cisq1.lingo.trainer.data;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRepositoryTest {
    @Test
    void findByIdWhenNotFound() {
        // Given
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenThrow(new EntityNotFoundException(TrainerService.GAME_NOT_FOUND_MESSAGE));
        // When Then
        assertThrows(
                EntityNotFoundException.class,
                () -> mockRepository.findById(1L)

        );
    }

    @Test
    void findByIdWhenFound() {
        // Given
        Game game = Game.playing("woord");
        Optional<Game> expectedResult = Optional.of(game);
        GameRepository mockRepository = mock(GameRepository.class);
        when(mockRepository.findById(1L))
                .thenReturn(Optional.of(game));
        // When
        Optional<Game> result = mockRepository.findById(1L);
        // Then
        assertEquals(expectedResult, result);
    }

}