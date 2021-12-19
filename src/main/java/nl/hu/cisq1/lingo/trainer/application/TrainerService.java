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

    private WordService wordService;
    private GameRepository gameRepository;

    public TrainerService(WordService wordService, GameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public Game startGame(){
        String wordToGuess = wordService.provideRandomWord(5);
        Game game = new Game();
        game.startNewRound(wordToGuess);
        return gameRepository.save(game);
    }

    public Game guessWord(Long gameId, String guessedWord){
        Game game = this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game note found"));
        game.guess(guessedWord);
        return game;
    }

    public Progress getProgress(Long gameId){
        return this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"))
                .showProgress();
    }
}
