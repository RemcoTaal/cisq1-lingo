package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Progress {
    int score;
    List<Character> hints;
    int roundNumber;

    public Progress(int score, List<Character> hints, int roundNumber) {
        this.score = score;
        this.hints = hints;
        this.roundNumber = roundNumber;
    }
}
