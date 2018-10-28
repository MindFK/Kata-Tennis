package org.kata.services.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.Game;
import org.kata.entities.GameScore;
import org.kata.entities.Player;
import org.kata.entities.Score;
import org.kata.enums.GameState;
import org.kata.services.score.GameScoreByPlayerIndex;
import org.kata.services.score.GameScoreService;
import org.kata.services.score.SetScoreByPlayerIndex;
import org.kata.services.score.SetScoreService;

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
    private AutoPlayer pointWinner;
    private AutoPlayer setWinner;
    private AutoPlayer matchWinner;

    private GameScoreService gameScoreService;
    private Map<Player, AutoPlayer> playersMap;
    private SetScoreService setScoreService;

    private GameState pointState = GameState.DEFAULT;
    private GameState gameState = GameState.DEFAULT;
    private GameState setState = GameState.DEFAULT;
    private GameState matchState = GameState.DEFAULT;


    public GameScore getGameScore() {
        return (GameScore) gameScoreService.getScore();
    }

    public Score getSetScore() {
        return setScoreService.getScore();
    }

    public GameScoreByPlayerIndex getGameScoreByIndex() {
        return gameScoreService.getScoreByPlayerIndex();
    }

    public SetScoreByPlayerIndex getSetScoreByIndex() {
        return setScoreService.getScoreByPlayerIndex();
    }

    public void startAPoint() {
        checkMatchIsReady();
        pointState = GameState.STARTED;
        getPlayers().forEach(a -> a.setLostThePoint(false));
        this.pointWinner = null;
    }

    public void startASet() {
        checkMatchIsReady();
        if (!setState.equals(GameState.STARTED))
            log.debug("New Set started");
        setState = GameState.STARTED;
        gameScoreService.initTheScore();
        this.setWinner = null;
        startAPoint();
    }

    public void startAMatch() {
        checkMatchIsReady();
        if (!matchState.equals(GameState.STARTED))
            log.debug("New Match started");
        setScoreService.initTheScore();
        matchState = GameState.STARTED;
        matchWinner = null;
        startASet();
    }

    public void checkSetIsStarted() {
        if (!GameState.STARTED.equals(setState))
            throw new RuntimeException("Set is not started");
    }

    public void checkMatchIsStarted() {
        if (!GameState.STARTED.equals(matchState))
            throw new RuntimeException("Match is not started or already terminated");
    }

    public void terminateMatch() {
        matchState = GameState.ENDED;
        log.debug("Match ended");
    }

    public void terminateSet() {
        setState = GameState.ENDED;
        log.debug("Set ended");

    }

    public void terminateGame() {
        gameState = GameState.ENDED;
    }

    public void terminatePoint() {
        pointState = GameState.ENDED;
    }

    public boolean isSetStarted() {
        return GameState.STARTED.equals(setState);
    }

    public boolean isMatchStarted() {
        return GameState.STARTED.equals(matchState);
    }

    public boolean isGameStarted() {
        return GameState.STARTED.equals(gameState);
    }

    public boolean isPointStarted() {
        return GameState.STARTED.equals(pointState);
    }

    protected void checkMatchIsReady() {
        if (model.getPlayer1() == null || model.getPlayer2() == null) {
            matchState = GameState.DEFAULT;
            throw new RuntimeException("Game is not ready");
        }
    }

    public GameService(Game game) {
        this.model = game;
        this.gameScoreService = new GameScoreService(game.getGameScore());
        this.setScoreService = new SetScoreService(game.getSetScore());
    }

    private Map<Player, AutoPlayer> getPlayersMap() {
        if (playersMap == null) {
            playersMap = new HashMap<>();
            this.playersMap.put(model.getPlayer1(), AutoPlayer.wrap(model.getPlayer1()));
            this.playersMap.put(model.getPlayer2(), AutoPlayer.wrap(model.getPlayer2()));
        }
        return this.playersMap;
    }

    public AutoPlayer getMatchWinner() {
        return matchWinner;
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
        AutoPlayer player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        gameScoreService.applyRules(index);
        if (gameScoreService.getWinner()) {
            setWinner = player;
            log.debug(player + " win the set");
            terminateSet();
            applySetScoreRules(player);
        }
    }

    public void applySetScoreRules(Player pr) {
        AutoPlayer player = getPlayersMap().get(pr);
        int index = getPlayers().indexOf(player);
        setScoreService.applyRules(index);
        if (setScoreService.getWinner()) {
            matchWinner = player;
            log.debug(player + " win the match");
            terminateMatch();
        }
    }


    protected void setPointWinner(AutoPlayer pointWinner) {
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

    public List<AutoPlayer> getPlayers() {
        List<AutoPlayer> players = new ArrayList<>();
        Map<Player, AutoPlayer> playersMap = getPlayersMap();
        players.add(playersMap.get(model.getPlayer1()));
        players.add(playersMap.get(model.getPlayer2()));
        return players;
    }

    @Override
    public String toString() {
        return "GameService{" +
                "model=" + model +
                '}';
    }
}