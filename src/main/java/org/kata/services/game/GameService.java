package org.kata.services.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.Game;
import org.kata.entities.GameScore;
import org.kata.entities.Player;
import org.kata.enums.GameState;
import org.kata.services.score.GameScoreByPlayerIndex;
import org.kata.services.score.GameScoreStarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:37 PM.
 */
public class GameService {
    final static Logger log = LogManager.getLogger(GameService.class);
    private Game model;
    private AutoGame pointWinner;
    private AutoGame setWinner;

    private GameScoreStarter gameScoreExecutor;
    private Map<Player, AutoGame> playersMap;//keep the order the same with the Game

    private GameState pointState = GameState.DEFAULT;
    private GameState gameState = GameState.DEFAULT;
    private GameState setState = GameState.DEFAULT;

    public GameScore getGameScore() {
        return (GameScore) gameScoreExecutor.getScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex() {
        return gameScoreExecutor.getScoreByPlayerIndex();
    }

    public void startAPoint() {
        pointState = GameState.STARTED;
        getPlayers().forEach(a -> a.setLostThePoint(false));
        this.pointWinner = null;
        log.debug("New Point started");
    }

    public void startASet() {
        setState = GameState.STARTED;
        gameScoreExecutor.initTheScore();
        this.setWinner = null;
        log.debug("New set started");
        startAPoint();
    }

    public void checkSetIsStarted() {
        if (!GameState.STARTED.equals(setState))
            throw new RuntimeException("Set is not started");
    }


    public void terminateSet() {
        setState = GameState.ENDED;
        terminateGame();
    }

    public void terminateGame() {
        gameState = GameState.ENDED;
        terminatePoint();
    }

    public void terminatePoint() {
        pointState = GameState.ENDED;
    }

    public boolean isSetStarted() {
        return GameState.STARTED.equals(setState);
    }

    public boolean isGameStarted() {
        return GameState.STARTED.equals(gameState);
    }

    public boolean isPointStarted() {
        return GameState.STARTED.equals(pointState);
    }

    public GameService(Game game) {
        this.model = game;
        this.gameScoreExecutor = new GameScoreStarter(game.getGameScore());
    }

    private Map<Player, AutoGame> getPlayersMap() {
        if (playersMap == null) {
            playersMap = new HashMap<>();
            this.playersMap.put(model.getPlayer1(), AutoGame.wrap(model.getPlayer1()));
            this.playersMap.put(model.getPlayer2(), AutoGame.wrap(model.getPlayer2()));
        }
        return this.playersMap;
    }

    public Game getModel() {
        return model;
    }

    public Player getSetWinner() {
        return setWinner;
    }

    public Player getPointWinner() {
        return pointWinner;
    }

    public void applyGameScoreRules(Player pr) {
        AutoGame player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        gameScoreExecutor.applyRules(index);
        if (gameScoreExecutor.getWinner()) {
            setWinner = player;
            log.debug(player + " win the set");
            terminateSet();
        }
    }

    protected void setPointWinner(AutoGame pointWinner) {
        this.pointWinner = pointWinner;
    }

    public int getPlayerIndex(Player player) {
        if (player.equals(model.getPlayer1()))
            return 0;
        else if (player.equals(model.getPlayer2()))
            return 1;
        else
            throw new RuntimeException("Player not founds");
    }

    public List<AutoGame> getPlayers() {
        List<AutoGame> players = new ArrayList<>();
        Map<Player, AutoGame> playersMap = getPlayersMap();
        players.add(playersMap.get(model.getPlayer1()));
        players.add(playersMap.get(model.getPlayer2()));
        return players;
    }

    @Override
    public String toString() {
        return "GameService{" +
                "model=" + model +
                ", pointWinner=" + pointWinner +
                ", setWinner=" + setWinner +
                ", gameScoreExecutor=" + gameScoreExecutor +
                ", playersMap=" + playersMap +
                ", pointState=" + pointState +
                ", gameState=" + gameState +
                ", setState=" + setState +
                '}';
    }
}
