package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {
    @MockBean
    private SpringWordRepository wordRepository;

    @MockBean
    private GameRepository gameRepository;

    @SpyBean
    private Game spy;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void startGame() throws Exception {
        // Given
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("woord")));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/games");

        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.feedbackHistory", hasSize(0)))
                .andExpect(jsonPath("$.currentHint", hasSize(5)))
                .andExpect(jsonPath("$.roundNumber", is(1)));

    }

    @Test
    void guess() throws Exception {
        // Given
        when(gameRepository.findById(1L))
                .thenReturn(Optional.of(Game.playing("woord")));

        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/games/{id}/guess", 1)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(0)))
                .andExpect(jsonPath("$.feedbackHistory", hasSize(1)))
                .andExpect(jsonPath("$.currentHint", hasSize(5)))
                .andExpect(jsonPath("$.roundNumber", is(1)));
    }

    @Test
    void guessInvalidAttempt() throws Exception {
        // Given
        when(gameRepository.findById(1L))
                .thenReturn(Optional.of(Game.eliminated()));

        when(wordRepository.findWordByValue("woord"))
                .thenReturn(Optional.of(new Word("woord")));
        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/games/{id}/guess", 1)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    void guessWhenNoActiveRound() throws Exception {
        // Given
        when(gameRepository.findById(1L))
                .thenReturn(Optional.of(Game.waitingForRound()));
        when(wordRepository.findWordByValue("woord"))
                .thenReturn(Optional.of(new Word("woord")));

        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/games/{id}/guess", 1)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    void guessWhenGameNotFound() throws Exception {
        // Given
        when(gameRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("Game not found"));
        when(wordRepository.findWordByValue("woord"))
                .thenReturn(Optional.of(new Word("woord")));

        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/games/{id}/guess", 1)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    void getProgress() throws Exception {
        // Given
        // Ik kreeg het niet voor elkaar om een nested method te testen :(
        Game game = Game.withProgress();
        spy = spy(game);

        doReturn(game.showProgress()).when(spy).showProgress();
        when(gameRepository.findById(1L))
                .thenReturn(Optional.of(spy));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/{id}/progress", 1);
        // When
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("PLAYING")))
                .andExpect(jsonPath("$.score", is(50)))
                .andExpect(jsonPath("$.feedbackHistory", hasSize(2)))
                .andExpect(jsonPath("$.currentHint", hasSize(7)))
                .andExpect(jsonPath("$.roundNumber", is(3)));
    }

    @Test
    void getProgressWhenGameNotFound() throws Exception {
        // Given
        when(gameRepository.findById(1L))
                .thenThrow(new EntityNotFoundException("Game not found"));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/{id}/progress", 1);
        // When
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }


}