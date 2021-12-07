package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidHintException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private final String attempt;
    private final List<LetterFeedback> letterFeedbackList;

    public Feedback(String attempt, List<LetterFeedback> letterFeedbackList){
        if (!isSameLength(attempt, letterFeedbackList)) {
            throw InvalidFeedbackException.wrongLength(attempt, letterFeedbackList);
        }
        this.attempt = attempt;
        this.letterFeedbackList = letterFeedbackList;
    }

    public static Feedback valid(String attempt){
        List<LetterFeedback> letterFeedbackList = new ArrayList<>();
        for (int i = 0; i<attempt.length(); i++){
            letterFeedbackList.add(LetterFeedback.CORRECT);
        }
        return new Feedback(attempt, letterFeedbackList);
    }

    public static Feedback invalid(String attempt){
        List<LetterFeedback> letterFeedbackList = new ArrayList<>();
        for (int i = 0; i<attempt.length() + 1; i++){
            letterFeedbackList.add(LetterFeedback.INVALID);
        }
        return new Feedback(attempt, letterFeedbackList);
    }

    public List<Character> giveHint(List<Character> previousHint, String wordToGuess) throws InvalidHintException {
        // Throw exception if previoushint length does not match word to guess length or letterfeedback list size
        if (previousHint.size() != letterFeedbackList.size()) {
            throw InvalidHintException.wrongPreviousHintAndLetterFeedbackListLength(previousHint, letterFeedbackList);
        }
        // Throw exception if word to guess length does not match the letterfeedback list size
        if (wordToGuess.length() != letterFeedbackList.size()) {
            throw InvalidHintException.wrongLetterFeedbackListAndWordToGuessLength(letterFeedbackList, wordToGuess);
        }

        // Make a new arraylist so it will be mutable
        List<Character> newHint = new ArrayList<>(previousHint);

        // Loop over the length of the word ans compare to the given feedback
        for (int i = 0; i < wordToGuess.length(); i++){
            if (letterFeedbackList.get(i) == LetterFeedback.CORRECT && previousHint.get(i) == '.'){
                newHint.set(i, wordToGuess.charAt(i));
            }
        }

        return newHint;
    }

    public boolean isWordGuessed(){
        return letterFeedbackList
                .stream()
                .allMatch(letterFeedback -> letterFeedback.equals(LetterFeedback.CORRECT));
    }

    public boolean isGuessValid(){
        return letterFeedbackList
                .stream()
                .noneMatch(letterFeedback -> letterFeedback.equals(LetterFeedback.INVALID));
    }

    public boolean isSameLength(String attempt, List<LetterFeedback> letterFeedbackList){
        return attempt.length() == letterFeedbackList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return attempt.equals(feedback.attempt) && letterFeedbackList.equals(feedback.letterFeedbackList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, letterFeedbackList);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", letterFeedbackList=" + letterFeedbackList +
                '}';
    }

    public List<LetterFeedback> getLetterFeedbackList() {
        return letterFeedbackList;
    }
}
