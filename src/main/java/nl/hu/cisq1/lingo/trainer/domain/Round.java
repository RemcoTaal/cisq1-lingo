package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private String wordToGuess;
    private final List<Feedback> feedbackHistory = new ArrayList<>();

    public Round() { }

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public Feedback giveFeedback(String guessedWord) {
        if (!guessedWordIsCorrectLength(guessedWord)) {
            return giveFeedbackInvalidWord(guessedWord);
        }
        List<LetterFeedback> letterFeedbackList = checkCharPositions(guessedWord);
        return new Feedback(guessedWord, letterFeedbackList);
    }

    public void newAttempt(String guessedWord){

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

        for (char guessedChar : guessedWordChars){
            for (char toGuessChar : wordToGuessChars){
                if (guessedChar == toGuessChar){
                    letterFeedbackList.add(LetterFeedback.CORRECT);
                }
                else if (wordToGuess.contains(String.valueOf(guessedChar))){
                    letterFeedbackList.add(LetterFeedback.PRESENT);
                }
                else {
                    letterFeedbackList.add(LetterFeedback.ABSENT);
                }
            }
        }
        return letterFeedbackList;
    }

    public void addFeedbackToHistory(Feedback feedback){
        this.feedbackHistory.add(feedback);
    }

    public boolean guessedWordIsCorrectlySpelled(String guessedWord){
        return true;
    }

    public boolean guessedWordIsCorrectLength(String guessedWord){
        return wordToGuess.length() == guessedWord.length();
    }

}
