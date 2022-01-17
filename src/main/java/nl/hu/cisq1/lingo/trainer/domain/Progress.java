package nl.hu.cisq1.lingo.trainer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Progress implements Serializable {
    int score;
    List<Character> currentHint;
    int roundNumber;

    public Progress(int score, List<Character> currentHint, int roundNumber) {
        this.score = score;
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
        return score == progress.score && roundNumber == progress.roundNumber && Objects.equals(currentHint, progress.currentHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, currentHint, roundNumber);
    }

    @Override
    public String toString() {
        return "Progress{" +
                "score=" + score +
                ", currentHint=" + currentHint +
                ", roundNumber=" + roundNumber +
                '}';
    }
}
