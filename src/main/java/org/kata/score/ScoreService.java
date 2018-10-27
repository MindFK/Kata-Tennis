package org.kata.score;

import org.kata.entities.Score;

/**
 * Created by Walid GHARIANI on 10/27/2018-12:18 AM.
 */
public abstract class ScoreService {
    private Score score;
    public Score getScore(){
        return score;
    }

    public ScoreService(Score score){
        this.score = score;
    }

    private boolean isWinner;

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    public boolean getWinner(){
        return isWinner;
    }

    public abstract void applyRules(int playerIndex);

    protected abstract int nextScore(int score);

    public void initTheScore(){
        setWinner(false);
    }

}
