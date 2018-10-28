package org.kata.entities;

import java.util.Date;

/**
 * Created by Walid GHARIANI on 10/26/2018-10:37 PM.
 */
public class Game {
    private Player player1;
    private Player player2;
    private Date date = new Date();
    private GameScore gameScore = new GameScore();
    private SetScore setScore = new SetScore();

    public Game() {
        gameScore.setGame(this);
        setScore.setGame(this);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GameScore getGameScore() {
        return gameScore;
    }

    public SetScore getSetScore() {
        return setScore;
    }

    public void setSetScore(SetScore setScore) {
        this.setScore = setScore;
    }

    public void setGameScore(GameScore gameScore) {
        this.gameScore = gameScore;
    }

    public void setPlayerScore(int index, int score) {
        if (index == 0)
            this.gameScore.setScorePlayer1(score);
        else if (index == 1)
            this.gameScore.setScorePlayer2(score);
        else throw new RuntimeException("Invalid index");
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return "Game{" +
                //    "date=" + date +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", setSetScore=" + gameScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (player1 != null ? !player1.equals(game.player1) : game.player1 != null) return false;
        if (player2 != null ? !player2.equals(game.player2) : game.player2 != null) return false;
        if (date != null ? !date.equals(game.date) : game.date != null) return false;
        return gameScore != null ? gameScore.equals(game.gameScore) : game.gameScore == null;

    }

    @Override
    public int hashCode() {
        int result = player1 != null ? player1.hashCode() : 0;
        result = 31 * result + (player2 != null ? player2.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (gameScore != null ? gameScore.hashCode() : 0);
        return result;
    }

}
