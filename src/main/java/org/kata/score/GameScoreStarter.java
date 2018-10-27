package org.kata.score;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.GameScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:56 PM.
 */
public class GameScoreStarter extends ScoreService {
    final static Logger log = LogManager.getLogger(GameScoreStarter.class);
    protected GameScoreByPlayerIndex scoreByPlayerIndex;

    public GameScoreStarter(GameScore score) {
        super(score);
        this.scoreByPlayerIndex = new GameScoreByPlayerIndex(score);
    }

    public static List<Integer> STEPS = new ArrayList(Arrays.asList(new Integer[]{0, 15, 30, 40}));

    public GameScoreByPlayerIndex getScoreByPlayerIndex() {
        return scoreByPlayerIndex;
    }

    @Override
    public void initTheScore() {
        super.initTheScore();
        GameScore gameScore = (GameScore) scoreByPlayerIndex.getScore();
        gameScore.setScorePlayer1(0);
        gameScore.setScorePlayer2(0);
        gameScore.setDeucePlayer1(false);
        gameScore.setDeucePlayer2(false);
    }

    @Override
    public void applyRules(int playerIndex) {
        int indexOfOtherPlayer = 1 - playerIndex;
        int currentScore = scoreByPlayerIndex.getScore(playerIndex);
        int newScore = nextScore(currentScore);
        if (currentScore == 40) {
            setWinner(true);
        }
        scoreByPlayerIndex.setScore(playerIndex, newScore);
        log.debug("New Game Score : " + scoreByPlayerIndex.getScore());
    }

    @Override
    protected int nextScore(int score) {
        int index = STEPS.indexOf(score);
        if (index < STEPS.size() - 1)
            return STEPS.get(index + 1);
        return STEPS.get(index);
    }

}