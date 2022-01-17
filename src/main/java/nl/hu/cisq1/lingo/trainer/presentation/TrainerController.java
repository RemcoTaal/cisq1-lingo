package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("game")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Progress startGame() {
        return trainerService.startGame();
    }

    @PostMapping(value = "/{id}/round", produces = MediaType.APPLICATION_JSON_VALUE)
    public Progress startNewRound(@PathVariable Long id) {
        try {
            return trainerService.startNewRound(id);
        }
        catch (RoundException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/{id}/guess", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Progress guess(@PathVariable Long id, @RequestBody Map<String, String> json) {
        try {
            return trainerService.guessWord(id, json.get("guessedWord"));
        }
        catch (InvalidAttemptException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
        catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping(value = "/{id}/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public Progress getProgress(@PathVariable Long id) {
        try {
            return trainerService.getProgress(id);
        }
        catch (EntityNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }


}
