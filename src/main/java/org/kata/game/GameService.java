package org.kata.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.Game;
import org.kata.entities.GameScore;
import org.kata.entities.Player;
import org.kata.enums.GameStatus;
import org.kata.score.GameScoreByPlayerIndex;
import org.kata.score.GameScoreStarter;

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
    private PlayerService pointWinner;
    private PlayerService setWinner;

    private GameScoreStarter gameScoreExecutor;
    private Map<Player, PlayerService> playersMap;//keep the order the same with the Game sg.kata.model

    private GameStatus pointState = GameStatus.DEFAULT;
    private GameStatus gameState = GameStatus.DEFAULT;
    private GameStatus setState = GameStatus.DEFAULT;

    public GameScore getGameScore() {
        return (GameScore) gameScoreExecutor.getScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex() {
        return gameScoreExecutor.getScoreByPlayerIndex();
    }

    public void startAPoint() {
        pointState = GameStatus.STARTED;
        getPlayers().forEach(a -> a.setLostThePoint(false));
        this.pointWinner = null;
        log.debug("New Point started");
    }

    public void startASet() {
        setState = GameStatus.STARTED;
        gameScoreExecutor.initTheScore();
        this.setWinner = null;
        log.debug("New set started");
        startAPoint();
    }

    public void checkSetIsStarted() {
        if (!GameStatus.STARTED.equals(setState))
            throw new RuntimeException("Set is not started or already terminated");
    }


    public void terminateSet() {
        setState = GameStatus.ENDED;
        terminateGame();
    }

    public void terminateGame() {
        gameState = GameStatus.ENDED;
        terminatePoint();
    }

    public void terminatePoint() {
        pointState = GameStatus.ENDED;
    }

    public boolean isSetStarted() {
        return GameStatus.STARTED.equals(setState);
    }

    public boolean isGameStarted() {
        return GameStatus.STARTED.equals(gameState);
    }

    public boolean isPointStarted() {
        return GameStatus.STARTED.equals(pointState);
    }

    public GameService(Game game) {
        this.model = game;
        this.gameScoreExecutor = new GameScoreStarter(game.getGameScore());
    }

    private Map<Player, PlayerService> getPlayersMap() {
        if (playersMap == null) {
            playersMap = new HashMap<>();
            this.playersMap.put(model.getPlayer1(), PlayerService.wrap(model.getPlayer1()));
            this.playersMap.put(model.getPlayer2(), PlayerService.wrap(model.getPlayer2()));
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
        PlayerService player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        gameScoreExecutor.applyRules(index);
        if (gameScoreExecutor.getWinner()) {
            setWinner = player;
            log.debug(player + " win the set");
            terminateSet();
        }
    }

    protected void setPointWinner(PlayerService pointWinner) {
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

    public List<PlayerService> getPlayers() {
        List<PlayerService> players = new ArrayList<>();
        Map<Player, PlayerService> playersMap = getPlayersMap();
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