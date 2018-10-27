package org.kata.services.score;

import org.kata.entities.Player;
import org.kata.entities.Score;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:05 PM.
 */
public class ScoreByPlayerIndex {
    protected Score score;
    private PlayerByIndex playerByIndex;
    public Score getScore() {
        return score;
    }

    public ScoreByPlayerIndex(Score score){
        this.score = score;
        this.playerByIndex = new PlayerByIndex(score.getGame());
    }

    public Player getPlayer(int index){
        return playerByIndex.getPlayer(index);
    }

    public int getScore(int index){
        if (index==0)
            return score.getScorePlayer1();
        else if (index==1)
            return score.getScorePlayer2();
        else
            throw new RuntimeException("Invalid index");
    }

    public void setScore(int index, int score_){
        if (index==0)
            score.setScorePlayer1(score_);
        else if (index==1)
            score.setScorePlayer2(score_);
        else
            throw new RuntimeException("Invalid index");
    }
}
