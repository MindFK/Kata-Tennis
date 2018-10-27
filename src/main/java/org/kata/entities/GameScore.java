package org.kata.entities;

/**
 * Created by Walid GHARIANI on 10/27/2018-1:20 AM.
 */
public class GameScore extends Score {
    private boolean deucePlayer1;
    private boolean deucePlayer2;

    public boolean isDeucePlayer1() {
        return deucePlayer1;
    }

    public void setDeucePlayer1(boolean deucePlayer1) {
        this.deucePlayer1 = deucePlayer1;
    }

    public boolean isDeucePlayer2() {
        return deucePlayer2;
    }

    public void setDeucePlayer2(boolean deucePlayer2) {
        this.deucePlayer2 = deucePlayer2;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "Player1(" + getGame().getPlayer1().getName() + ")={score=" + getScorePlayer1() + ", deuce=" + deucePlayer1 + "}," +
                "Player2(" + getGame().getPlayer2().getName() + ")={score=" + getScorePlayer2() + ", deuce=" + deucePlayer2 + "}" +
                '}';
    }
}