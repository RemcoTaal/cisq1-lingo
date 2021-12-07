package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Round {
    private String wordToGuess;
    private final List<Feedback> feedbackHistory = new ArrayList<>();
    private int attempts;

    public Round() { }

    public Round(String wordToGuess) {
        this.attempts = 0;
        this.wordToGuess = wordToGuess;
    }

    public void guess(String guessedWord){
        this.attempts++;
        if (!isValidAttempt()) {
            throw InvalidAttemptException.limitExceeded(this);
        }
        Feedback feedback = giveFeedback(guessedWord);
        addFeedbackToHistory(feedback);
    }

    public Feedback giveFeedback(String guessedWord) {
        if (!guessedWordIsCorrectLength(guessedWord)) {
            return giveFeedbackInvalidWord(guessedWord);
        }
        List<LetterFeedback> letterFeedbackList = checkCharPositions(guessedWord);
        return new Feedback(guessedWord, letterFeedbackList);
    }

    public Feedback giveFeedbackInvalidWord(String guessedWord) {
        List<LetterFeedback> letterFeedbackList = new ArrayList<>();
        guessedWord.chars()
                .forEach(character -> letterFeedbackList.add(LetterFeedback.INVALID));
        return new Feedback(guessedWord, letterFeedbackList);
    }

    public List<LetterFeedback> checkCharPositions(String guessedWord) {
        List<LetterFeedback> letterFeedbackList = new ArrayList<>();
        char[] guessedWordChars = guessedWord.toCharArray();
        char[] wordToGuessChars = wordToGuess.toCharArray();

        for (int i = 0; i < wordToGuessChars.length; i++){
            if (guessedWordChars[i] == wordToGuessChars[i]) {
                letterFeedbackList.add(LetterFeedback.CORRECT);
            }
            else if (wordToGuess.contains(String.valueOf(guessedWordChars[i]))){
                letterFeedbackList.add(LetterFeedback.PRESENT);
            }
            else {
                letterFeedbackList.add(LetterFeedback.ABSENT);
            }
        }

        return letterFeedbackList;
    }

    public boolean isValidAttempt(){
        return getAttempts() <= 5;
    }

    public void addFeedbackToHistory(Feedback feedback){
        this.feedbackHistory.add(feedback);
    }

    public List<Feedback> getFeedbackHistory(){
        return this.feedbackHistory;
    }

    public boolean guessedWordIsCorrectlySpelled(String guessedWord){
        return true;
    }

    public boolean guessedWordIsCorrectLength(String guessedWord){
        return wordToGuess.length() == guessedWord.length();
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCurrentWordLength() {
        return wordToGuess.length();
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
