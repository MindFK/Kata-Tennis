package org.kata.entities;

/**
 * Created by Walid GHARIANI on 10/26/2018-10:42 PM.
 */
public abstract class Score {
    private int scorePlayer1;
    private int scorePlayer2;
    private Game game;

    public Score() {

    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Score{" +
                "scoreForPlayer1=" + scorePlayer1 +
                ", scoreForPlayer2=" + scorePlayer2 +
                ", game=" + game +
                '}';
    }
}
