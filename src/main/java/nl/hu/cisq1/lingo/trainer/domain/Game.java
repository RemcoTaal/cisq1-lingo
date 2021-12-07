package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Round> roundHistory = new ArrayList<>();
    Round currentRound;
    GameStatus status;


    public static Game playing(String wordToGuess) {
        Game game = new Game();
        game.currentRound = new Round(wordToGuess);
        game.status = GameStatus.PLAYING;
        return game;
    }

    public static Game eliminated(){
        Game game = new Game();
        game.status = GameStatus.ELIMINATED;
        return game;
    }

    public Game() {
        this.status = GameStatus.WAITING_FOR_ROUND;
    }

    public void startNewRound(String wordToGuess){
        if (this.status != GameStatus.WAITING_FOR_ROUND){
            // TODO throw new exception still active round
        }
        currentRound = new Round(wordToGuess);
        this.status = GameStatus.PLAYING;
    }

    public void guess(String guessedWord){
        currentRound.guess(guessedWord);
        this.addRoundToHistory(currentRound);
    }

    public void addRoundToHistory(Round round){
        this.roundHistory.add(round);
    }

    public List<Round> getRoundHistory() {
        return roundHistory;
    }

    public Progress showProgress(){
        return new Progress(1, List.of('t'), 1);
    }

    public boolean isPlayerEliminated(){
        return this.status == GameStatus.ELIMINATED;
    }

    public boolean isPlaying(){
        return this.status == GameStatus.PLAYING;
    }

    public int provideNextWordLength(){
        return this.currentRound.getCurrentWordLength() + 1;
    }



}
