package org.kata.game;

import org.kata.entities.Game;

import java.util.List;
import java.util.Random;

/**
 * Created by Walid GHARIANI on 10/27/2018-12:56 AM.
 */
public class AutoTennisGame extends GameService {

    public AutoTennisGame(Game game) {
        super(game);
    }

    private PlayerService findThePointWinner() {
        List<PlayerService> players = getPlayers();
        for (PlayerService PlayerService : players)
            if (PlayerService.hasLostThePoint())
                return getPlayers().stream().filter(p -> !p.hasLostThePoint()).findAny().orElse(null);
        return null;
    }

    private void playAPoint() {
        int ballPosition = getPlayers().get(new Random().nextInt(1)).sendTheService();
        PlayerService pointWinner = findThePointWinner();
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
}

