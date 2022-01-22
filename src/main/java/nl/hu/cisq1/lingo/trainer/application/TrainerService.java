package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TrainerService {
    private static final String GAME_NOT_FOUND_MESSAGE = "Game not found";
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

    public Progress startNewRound(Long gameId){
        Game game = this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND_MESSAGE));
        int wordLength = game.provideNextWordLength();
        String wordToGuess = wordService.provideRandomWord(wordLength);
        game.startNewRound(wordToGuess);
        gameRepository.save(game);
        return game.showProgress();
    }

    public Progress guessWord(Long gameId, String guessedWord){
        Game game = this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND_MESSAGE));
        if (isCorrectlySpelled(guessedWord)){
            game.guess(guessedWord);
        } else {
            game.guessIncorrectlySpelledWord(guessedWord);
        }
        this.gameRepository.save(game);
        return game.showProgress();
    }

    public boolean isCorrectlySpelled(String word) {
        return wordService.wordExists(word);
    }

    public Progress getProgress(Long gameId){
        return this.gameRepository
                .findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException(GAME_NOT_FOUND_MESSAGE))
                .showProgress();
    }
}
