package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class TrainerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setup(@Autowired GameRepository gameRepository) {
        Game game = Game.playing("woord");
        gameRepository.save(game);

    }

    @AfterAll
    public static void breakDown(@Autowired GameRepository gameRepository) {

    }

    @Test
    void startGame() throws Exception {
        // Given
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game");

        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isOk());
    }

    @Test
    void guess() throws Exception {
        // Given
        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/{id}/guess", 1000)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void guessInvalidAttempt() throws Exception {
        // Given
        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/{id}/guess", 1001)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void guessWhenNoActiveRound() throws Exception {
        // Given
        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/{id}/guess", 1001)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void guessWhenGameNotFound() throws Exception {
        // Given
        String jsonBody = new JSONObject()
                .put("guessedWord", "woord")
                .toString();
        RequestBuilder request = MockMvcRequestBuilders
                .post("/game/{id}/guess", 9999)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON);
        // When
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProgress() throws Exception {
        // Given
        RequestBuilder request = MockMvcRequestBuilders
                .get("/{id}/progress", 1001);
        // When
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}