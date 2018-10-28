package org.kata.services.game;

import org.kata.entities.Game;

import java.util.List;
import java.util.Random;

/**
 * Created by Walid GHARIANI on 10/27/2018-12:56 AM.
 */
public class AutoGame extends GameService {

    public AutoGame(Game game) {
        super(game);
    }

    private AutoPlayer findThePointWinner() {
        List<AutoPlayer> players = getPlayers();
        for (AutoPlayer AutoPlayerService : players)
            if (AutoPlayerService.hasLostThePoint())
                return getPlayers().stream().filter(p -> !p.hasLostThePoint()).findAny().orElse(null);
        return null;
    }

    private void playAPoint() {
        int ballPosition = getPlayers().get(new Random().nextInt(1)).sendTheService();
        AutoPlayer pointWinner = findThePointWinner();
        int index = 1;
        while (pointWinner == null) {
            ballPosition = getPlayers().get(index).sendBack(ballPosition);
            pointWinner = findThePointWinner();
            index = 1 - index;
        }
        setPointWinner(pointWinner);

    }

    public void playASet() {
        startASet();
        while (getSetWinner() == null) {
            playAPoint();
            applyGameScoreRules(getPointWinner());
            if (getSetWinner() == null)
                startAPoint();
        }
    }

    public void playAMatch() {
        startAMatch();
        while (getMatchWinner() == null) {
            playASet();
        }
    }
}

