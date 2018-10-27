package org.kata.services.score;

import org.kata.entities.GameScore;
import org.kata.entities.Score;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:04 PM.
 */
public class GameScoreByPlayerIndex extends ScoreByPlayerIndex {
    public GameScoreByPlayerIndex(Score score) {
        super(score);
    }

    public boolean isDeuce(int index) {
        if (index == 0)
            return ((GameScore) score).isDeucePlayer1();
        else if (index == 1)
            return ((GameScore) score).isDeucePlayer2();
        else
            throw new RuntimeException("Invalid index");
    }

    public void setDeuce(int index, boolean score_) {
        if (index == 0)
            ((GameScore) score).setDeucePlayer1(score_);
        else if (index == 1)
            ((GameScore) score).setDeucePlayer2(score_);
        else
            throw new RuntimeException("Invalid index");
    }
}
