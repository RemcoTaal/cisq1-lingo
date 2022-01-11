package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TrainerService {

    private final WordService wordService;
    private final GameRepository gameRepository;

    public TrainerService(WordService wordService, GameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public Progress startGame(){
        String wordToGuess = wordService.provideRandomWord(5);
        Game game = new Game();
        game.startNewRound(wordToGuess);
        gameRepository.save(game);
        return game.showProgress();
    }

    public Progress guessWord(Long gameId, String guessedWord){
        Game game = this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
        game.guess(guessedWord);
        this.gameRepository.save(game);
        return game.showProgress();
    }

    public Progress getProgress(Long gameId){
        return this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"))
                .showProgress();
    }
}
