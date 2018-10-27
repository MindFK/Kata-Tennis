import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kata.entities.Game;
import org.kata.entities.GameScore;
import org.kata.entities.Player;
import org.kata.entities.Score;
import org.kata.controllers.HumanGameController;
import org.kata.services.score.GameScoreByPlayerIndex;

/**
 * Created by Walid GHARIANI on 10/27/2018-7:41 PM.
 */
public class GameTests {
    private Player p1 = new Player("Roger Federer");
    private Player p2 = new Player("Novak Djokovic");
    private static HumanGameController gameConsole = new HumanGameController();
    private static Game game;
    private static GameScore gameScore;
    private static GameScoreByPlayerIndex gameScoreByPlayerIndex;

    private static int default_winner_player_index = 1;

    @Before
    public void start() {
        gameConsole.initPlayersAndScores();
        gameConsole.joinTheGame(p1);
        gameConsole.joinTheGame(p2);
        game = gameConsole.getGameModel();
        gameScore = gameConsole.getGameScore();
        gameScoreByPlayerIndex = gameConsole.getGameScoreByIndex();
    }

    @Test
    public void testPlayTowGames() {
        gameConsole.startTheSet();
        playAGame(default_winner_player_index, false, 1, 0);
        playAGame(default_winner_player_index, false, 2, 0);
    }

    @Test
    public void testGameWinner() {
        gameConsole.startTheSet();
        playAGame(false, false);
        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointWinner() {
        gameConsole.startTheSet();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameConsole.getPointWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testSetWinner() {
        gameConsole.startTheSet();
        playAGame(default_winner_player_index, false, 1, 0);
        playAGame(default_winner_player_index, false, 2, 0);
        playAGame(default_winner_player_index, false, 3, 0);
        playAGame(default_winner_player_index, false, 4, 0);
        playAGame(default_winner_player_index, false, 5, 0);
        playAGame(default_winner_player_index, false, 6, 0);

        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointIncrementation() {
        gameConsole.startTheSet();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(default_winner_player_index), 15);
    }

    @Test
    public void testDeuceCase() {
        gameConsole.startTheSet();
        playAGame(true, false);
    }

    @Test
    public void testDeuceCaseAndLooseTheDeuce() {
        gameConsole.startTheSet();
        playAGame(true, true);
    }

    private void playAGame(boolean withDeuceCase, boolean withLooseTheDeuce) {
        playAGame(default_winner_player_index, withDeuceCase, 1, 0);
    }

    private void playAGame(int winnerIndex, boolean withDeuceCase, int expectedWinnerSetScore, int expectedLooserSetScore) {
        gameConsole.startTheSet();
        int looserIndex = 1 - winnerIndex;
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 0);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
        Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
        if (withDeuceCase) {
            gameConsole.winAPoint(looserIndex);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
            gameConsole.winAPoint(looserIndex);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), true);
            gameConsole.winAPoint(winnerIndex);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), false);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);
            gameConsole.winAPoint(winnerIndex);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 40);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(winnerIndex), true);
            Assert.assertEquals(gameScoreByPlayerIndex.isDeuce(looserIndex), false);


            gameConsole.winAPoint(winnerIndex);
            Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
        } else {
            gameConsole.winAPoint(winnerIndex);
            Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
        }
    }

    @Test(expected = RuntimeException.class)
    public void triggerSetNotStartedOrAlreadyTerminatedException() {
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
        gameConsole.winAPoint(default_winner_player_index);
    }
}
