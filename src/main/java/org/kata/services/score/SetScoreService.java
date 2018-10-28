package org.kata.services.score;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kata.entities.Score;
import org.kata.entities.SetScore;

/**
 * Created by Walid GHARIANI on 10/28/2018-1:25 AM.
 */
public class SetScoreService extends ScoreService {
    final static Logger log = LogManager.getLogger(SetScoreService.class);
    protected SetScoreByPlayerIndex scoreByPlayerIndex;

    public SetScoreService(SetScore score) {
        super(score);
        this.scoreByPlayerIndex = new SetScoreByPlayerIndex(score);
    }

    public SetScoreByPlayerIndex getScoreByPlayerIndex() {
        return scoreByPlayerIndex;
    }

    @Override
    public void initTheScore() {
        super.initTheScore();
        Score score = scoreByPlayerIndex.getScore();
        score.setScorePlayer1(0);
        score.setScorePlayer2(0);
    }

    @Override
    public void applyRules(int playerIndex) {
        int indexOfOtherPlayer = 1 - playerIndex;
        int currentScore = scoreByPlayerIndex.getScore(playerIndex);
        int newScore = nextScore(currentScore);
        if (newScore >= 6)
            if (newScore - scoreByPlayerIndex.getScore(indexOfOtherPlayer) >= 2) {
                setWinner(true);
            } else
                log.debug("Score gap < 2 -> Tie-Break continue ...");
        scoreByPlayerIndex.setScore(playerIndex, newScore);
        log.debug("New Set Score " + scoreByPlayerIndex.getScore());
    }

    @Override
    protected int nextScore(int score) {
        return score + 1;
    }
}
