package nl.hu.cisq1.lingo.trainer.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Progress implements Serializable {
    Long id;
    GameStatus status;
    int score;
    List<Feedback> feedbackHistory;
    List<Character> currentHint;
    int roundNumber;

    public Progress(Long id, GameStatus status, int score, List<Feedback> feedbackHistory, List<Character> currentHint, int roundNumber) {
        this.id = id;
        this.status = status;
        this.score = score;
        this.feedbackHistory = feedbackHistory;
        this.currentHint = currentHint;
        this.roundNumber = roundNumber;
    }

    public int getScore() {
        return score;
    }

    public List<Character> getCurrentHint() {
        return currentHint;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public List<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return Objects.equals(id, progress.id) && score == progress.score && Objects.equals(feedbackHistory, progress.feedbackHistory) && roundNumber == progress.roundNumber && Objects.equals(currentHint, progress.currentHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, score, feedbackHistory, currentHint, roundNumber);
    }

    @Override
    public String toString() {
        return "Progress{" +
                "gameId=" + id +
                ", score=" + score +
                ", feedbackHistory=" + feedbackHistory +
                ", currentHint=" + currentHint +
                ", roundNumber=" + roundNumber +
                '}';
    }
}
