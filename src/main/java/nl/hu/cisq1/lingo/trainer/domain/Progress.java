package nl.hu.cisq1.lingo.trainer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Progress implements Serializable {
    Long gameId;
    int score;
    List<Feedback> feedbackHistory;
    List<Character> currentHint;
    int roundNumber;

    public Progress(Long gameId, int score, List<Feedback> feedbackHistory, List<Character> currentHint, int roundNumber) {
        this.gameId = gameId;
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

    public Long getGameId() {
        return gameId;
    }

    public List<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return Objects.equals(gameId, progress.gameId) && score == progress.score && Objects.equals(feedbackHistory, progress.feedbackHistory) && roundNumber == progress.roundNumber && Objects.equals(currentHint, progress.currentHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, score, feedbackHistory, currentHint, roundNumber);
    }

    @Override
    public String toString() {
        return "Progress{" +
                "gameId=" + gameId +
                ", score=" + score +
                ", feedbackHistory=" + feedbackHistory +
                ", currentHint=" + currentHint +
                ", roundNumber=" + roundNumber +
                '}';
    }
}
