package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

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

    public List<Character> giveHint(List<Character> previousHint, String wordToGuess){
        // If there was no previous hint make a hint with the first character revealed
        if (previousHint == null) {
            previousHint = giveFirstHint(wordToGuess);
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

    public List<Character> giveFirstHint(String wordToGuess){
        // Create a new array
        List<Character> hint = new ArrayList<>();
        // Add the first character of the word to guess to the array
        hint.add(wordToGuess.charAt(0));

        // Loop over the length of the word starting from index 1
        for (int i = 1; i < wordToGuess.length(); i++){
            if (letterFeedbackList.get(i) == LetterFeedback.CORRECT){
                hint.add(wordToGuess.charAt(i));
            }
            else {
                hint.add('.');
            }
        }
        return hint;
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
}
