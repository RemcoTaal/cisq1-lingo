package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.ActiveRoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Round> rounds = new ArrayList<>();

    @OneToOne
    @JoinColumn
    Round currentRound;

    @Enumerated(EnumType.STRING)
    GameStatus status;

    public static Game playing(String wordToGuess) {
        Game game = new Game();
        game.startNewRound(wordToGuess);
        return game;
    }

    public static Game waitingForRound() {
        Game game = playing("woord");
        game.currentRound.guess("waard");
        game.currentRound.guess("wiird");
        game.currentRound.guess("woord");
        game.status = GameStatus.WAITING_FOR_ROUND;
        return game;
    }

    public static Game withProgress() {
        Game game = playing("woord");
        game.guess("woord");
        game.startNewRound("worden");
        game.guess("worden");
        game.startNewRound("schacht");
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
            throw ActiveRoundException.activeRound();
        }
        Round round = new Round(wordToGuess);
        this.addRound(round);
        currentRound = round;
        this.status = GameStatus.PLAYING;
    }

    public void guess(String guessedWord){
        // Check if attempt is valid
        switch(this.status){
            case ELIMINATED: {
                throw InvalidAttemptException.playerEliminated();
            }
            case WAITING_FOR_ROUND: {
                throw InvalidAttemptException.noActiveRound();
            }
        }

        // Try to perform the guess
        try {
            currentRound.guess(guessedWord);
            if (currentRound.isWordGuessed) {
                this.status = GameStatus.WAITING_FOR_ROUND;
            }
        } catch (InvalidAttemptException exception) {
            // Set the status to elimated when attempt limit was exceeded
            this.status = GameStatus.ELIMINATED;
            // Re throw the exception
            throw exception;
        }

    }

    public void addRound(Round round){
        this.rounds.add(round);
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Progress showProgress(){
        int score = calculateScore();
        List<Character> currentHint = currentRound.giveHint();
        int roundNr = this.getRoundNr();
        return new Progress(score, currentHint, roundNr);
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

    public int calculateScore(){
        int score = 0;
        for (Round round : rounds){
            if (round.isWordGuessed){
                score += 5 * (5 - round.getAttempts()) + 5;
            }
        }
        return score;
    }

    public int getRoundNr(){
        return this.rounds.size();
    }
}
