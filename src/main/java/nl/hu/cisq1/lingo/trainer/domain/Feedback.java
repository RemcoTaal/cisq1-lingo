package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;

public class Feedback {
    private final String attempt;
    private final List<LetterFeedback> letterFeedbackList;

    public Feedback(String attempt, List<LetterFeedback> letterFeedbackList){
        this.attempt = attempt;
        this.letterFeedbackList = letterFeedbackList;
    }

    public Boolean isWordGuessed(){
        return letterFeedbackList
                .stream()
                .allMatch(letterFeedback -> letterFeedback.equals(LetterFeedback.CORRECT));
    }

    public Boolean isValid(){
        return letterFeedbackList
                .stream()
                .noneMatch(letterFeedback -> letterFeedback.equals(LetterFeedback.INVALID));
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
