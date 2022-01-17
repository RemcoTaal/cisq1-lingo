package nl.hu.cisq1.lingo.trainer.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Progress implements Serializable {
    int gameId;
    int score;
    List<LetterFeedback> latestLetterFeedback;
    List<Character> currentHint;
    int roundNumber;

    public Progress(int gameId, int score, List<LetterFeedback> latestLetterFeedback, List<Character> currentHint, int roundNumber) {
        this.gameId = gameId;
        this.score = score;
        this.latestLetterFeedback = latestLetterFeedback;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return gameId == progress.gameId && score == progress.score && Objects.equals(latestLetterFeedback, progress.latestLetterFeedback) && roundNumber == progress.roundNumber && Objects.equals(currentHint, progress.currentHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, score, latestLetterFeedback, currentHint, roundNumber);
    }

    @Override
    public String toString() {
        return "Progress{" +
                "gameId=" + gameId +
                ", score=" + score +
                ", latestLetterFeedback=" + latestLetterFeedback +
                ", currentHint=" + currentHint +
                ", roundNumber=" + roundNumber +
                '}';
    }
}
