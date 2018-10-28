package org.kata.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.*;
import org.kata.services.score.GameScoreByPlayerIndex;
import org.kata.services.game.GameService;
import org.kata.services.score.SetScoreByPlayerIndex;

/**
 * Created by Walid GHARIANI on 10/27/2018-3:16 AM.
 */
public abstract class GameController {
    final static Logger log = LogManager.getLogger(GameService.class);

    protected GameService game;

    public void initPlayersAndScores() {
        log.debug("Init Players and scores");
        game.getModel().setPlayer1(null);
        game.getModel().setPlayer2(null);
        game.getModel().getGameScore().setScorePlayer1(0);
        game.getModel().getGameScore().setScorePlayer2(0);
        game.getModel().getGameScore().setDeucePlayer1(false);
        game.getModel().getGameScore().setDeucePlayer2(false);
    }

    public void joinTheGame(Player player) {
        if (game.getModel().getPlayer1() == null) {
            game.getModel().setPlayer1(player);
            log.debug(player + " has join the game as player1");
        } else if (game.getModel().getPlayer2() == null) {
            game.getModel().setPlayer2(player);
            log.debug(player + " has join the game as player2");
        } else
            throw new RuntimeException("Game is full, no empty place avalaible");
    }

    public void startThePoint() {
        game.startAPoint();
    }

    public void startTheSet() {
        game.startASet();
    }

    public void startTheMatch() {
        game.startAMatch();
    }

    public Player getSetWinner() {
        return game.getSetWinner();
    }


    public Player getSetLooser() {
        int winnerIndex = game.getPlayerIndex(getSetWinner());
        int looserIndex = 1 - winnerIndex;
        return game.getPlayers().get(looserIndex);
    }

    public int getSetWinnerScore() {
        int winnerIndex = game.getPlayerIndex(getSetWinner());
        return game.getGameScoreByIndex().getScore(winnerIndex);
    }

    public int getSetLooserScore() {
        int winnerIndex = game.getPlayerIndex(getSetLooser());
        return game.getGameScoreByIndex().getScore(winnerIndex);
    }

    public Player getPointWinner() {
        return game.getPointWinner();
    }

    public Game getGameModel() {
        return game.getModel();
    }

    public SetScore getSetScore() {
        return (SetScore) game.getSetScore();
    }

    public GameScore getGameScore() {
        return game.getGameScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex() {
        return game.getGameScoreByIndex();
    }

    public SetScoreByPlayerIndex getSetSocreByIndex() {
        return game.getSetScoreByIndex();
    }

    public int getMatchWinnerScore() {
        int winnerIndex = game.getPlayerIndex(getMatchWinner());
        return game.getSetScoreByIndex().getScore(winnerIndex);
    }

    public int getMatchLooserScore() {
        int winnerIndex = game.getPlayerIndex(getMatchLooser());
        return game.getSetScoreByIndex().getScore(winnerIndex);
    }

    public Player getMatchWinner() {
        return game.getMatchWinner();
    }

    public Player getMatchLooser() {
        int winnerIndex = game.getPlayerIndex(getMatchWinner());
        int looserIndex = 1 - winnerIndex;
        return game.getPlayers().get(looserIndex);
    }
}
