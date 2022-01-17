package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.data.converters.HintConverter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidAttemptException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String wordToGuess;

    @OneToMany
    @JoinColumn
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Feedback> feedbackHistory = new ArrayList<>();

    @Convert(converter = HintConverter.class)
    private List<Character> previousHint = new ArrayList<>();

    @Column
    private int attempts;

    @Column
    public boolean isWordGuessed = false;

    public Round() { }

    public Round(String wordToGuess) {
        this.attempts = 0;
        this.wordToGuess = wordToGuess;
        this.giveHint();
    }

    public static Round withFeedbackHistory(String wordToGuess) {
        Round round = new Round(wordToGuess);
        Feedback feedback = Feedback.valid("woord");
        round.feedbackHistory = List.of(feedback);
        return round;
    }

    public static Round differentAttempt(String wordToGuess) {
        Round round = new Round(wordToGuess);
        round.attempts = 2;
        return round;
    }

    public static Round differentFeedbackHistory(String wordToGuess) {
        Round round = new Round(wordToGuess);
        round.feedbackHistory = List.of(Feedback.valid("woord"));
        return round;
    }

    public void guess(String guessedWord){
        this.attempts++;
        if (!isValidAttempt()) {
            throw InvalidAttemptException.limitExceeded(this);
        }
        Feedback feedback = giveFeedback(guessedWord);
        addFeedbackToHistory(feedback);
        if (feedback.isWordGuessed()){
            this.isWordGuessed = true;
        }
    }

    public List<Character> giveHint(){
        if (feedbackHistory.size() == 0){
            return giveFirstHint();
        }
        Feedback lastFeedback = feedbackHistory.get(feedbackHistory.size() - 1);
        return lastFeedback.giveHint(previousHint, wordToGuess);
    }

    public List<Character> giveFirstHint(){
        // Create a new array
        List<Character> hint = new ArrayList<>();
        // Add the first character of the word to guess to the array
        hint.add(wordToGuess.charAt(0));
        // Loop over the length of the word starting from index 1
        for (int i = 1; i < wordToGuess.length(); i++){
            hint.add('.');
        }

        this.previousHint = hint;
        return hint;
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

    public List<LetterFeedback> getLatestLetterFeedback() {
        if (this.feedbackHistory.size() == 0){
            return null;
        }
        return this.feedbackHistory.get(this.feedbackHistory.size() - 1).getLetterFeedbackList();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return attempts == round.attempts && Objects.equals(wordToGuess, round.wordToGuess) && Objects.equals(feedbackHistory, round.feedbackHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordToGuess, feedbackHistory, attempts);
    }
}
