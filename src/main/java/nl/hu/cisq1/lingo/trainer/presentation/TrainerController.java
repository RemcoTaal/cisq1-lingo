package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.Map;

@RestController
@RequestMapping("game")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Game startGame() {
        return trainerService.startGame();
    }

    @PostMapping(value = "/{id}/guess", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Game guess(@PathVariable Long id, @RequestBody Map<String, String> json) {
        return trainerService.guessWord(id, json.get("guessedWord"));
    }

    @GetMapping(value = "/{id}/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public Progress getProgress(@PathVariable Long id) {
        return trainerService.getProgress(id);
    }


}
