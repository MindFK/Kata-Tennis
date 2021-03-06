import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kata.entities.Game;
import org.kata.entities.GameScore;
import org.kata.entities.Player;
import org.kata.controllers.HumanGameController;
import org.kata.entities.SetScore;
import org.kata.services.score.GameScoreByPlayerIndex;
import org.kata.services.score.SetScoreByPlayerIndex;

/**
 * Created by Walid GHARIANI on 10/27/2018-7:41 PM.
 */
public class GameTests {
    private Player p1 = new Player("Roger Federer");
    private Player p2 = new Player("Novak Djokovic");
    private static HumanGameController gameConsole = new HumanGameController();
    private static Game game;
    private static GameScore gameScore;
    private static SetScore setScore;
    private static GameScoreByPlayerIndex gameScoreByPlayerIndex;
    private static SetScoreByPlayerIndex setScoreByPlayerIndex;

    private static int default_winner_player_index = 1;

    @Before
    public void start() {
        gameConsole.initPlayersAndScores();
        gameConsole.joinTheGame(p1);
        gameConsole.joinTheGame(p2);
        game = gameConsole.getGameModel();
        setScore = gameConsole.getSetScore();
        gameScore = gameConsole.getGameScore();
        setScoreByPlayerIndex = gameConsole.getSetSocreByIndex();
        gameScoreByPlayerIndex = gameConsole.getGameScoreByIndex();
    }

    @Test
    public void testPlayTowGames() {
        gameConsole.startTheMatch();
        playAGame(default_winner_player_index, false, 1, 0);
        playAGame(default_winner_player_index, false, 2, 0);
    }

    @Test
    public void testGameWinner() {
        gameConsole.startTheMatch();
        playAGame(false, false);
        Assert.assertEquals(gameConsole.getSetWinner(), setScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointWinner() {
        gameConsole.startTheMatch();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameConsole.getPointWinner(), setScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testSetWinner() {
        gameConsole.startTheMatch();
        playAGame(default_winner_player_index, false, 1, 0);
        playAGame(default_winner_player_index, false, 2, 0);
        playAGame(default_winner_player_index, false, 3, 0);
        playAGame(default_winner_player_index, false, 4, 0);
        playAGame(default_winner_player_index, false, 5, 0);
        playAGame(default_winner_player_index, false, 6, 0);

        Assert.assertEquals(gameConsole.getSetWinner(), setScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointIncrementation() {
        gameConsole.startTheMatch();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(default_winner_player_index), 15);
    }

    @Test
    public void testDeuceCase() {
        gameConsole.startTheMatch();
        playAGame(true, false);
    }

    @Test
    public void testDeuceCaseAndLooseTheDeuce() {
        gameConsole.startTheMatch();
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
            Assert.assertEquals(setScoreByPlayerIndex.getScore(winnerIndex), expectedWinnerSetScore);
            Assert.assertEquals(setScoreByPlayerIndex.getScore(looserIndex), expectedLooserSetScore);
        } else {
            gameConsole.winAPoint(winnerIndex);
            Assert.assertEquals(setScoreByPlayerIndex.getScore(winnerIndex), expectedWinnerSetScore);
            Assert.assertEquals(setScoreByPlayerIndex.getScore(looserIndex), expectedLooserSetScore);
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

    @Test
    public void test6PointsVs5PointsCase() {
        int looserIndex = 1 - default_winner_player_index;
        gameConsole.startTheMatch();
        playAGame(looserIndex, false, 1, 0);
        playAGame(looserIndex, false, 2, 0);
        playAGame(looserIndex, false, 3, 0);
        playAGame(looserIndex, false, 4, 0);
        playAGame(looserIndex, false, 5, 0);
        playAGame(default_winner_player_index, false, 1, 5);
        playAGame(default_winner_player_index, false, 2, 5);
        playAGame(default_winner_player_index, false, 3, 5);
        playAGame(default_winner_player_index, false, 4, 5);
        playAGame(default_winner_player_index, false, 5, 5);
        playAGame(default_winner_player_index, false, 6, 5);
        Assert.assertEquals(gameConsole.getMatchWinner(), null);
        Assert.assertEquals(setScoreByPlayerIndex.getScore(looserIndex), 5);
        Assert.assertEquals(setScoreByPlayerIndex.getScore(default_winner_player_index), 6);
        playAGame(default_winner_player_index, false, 7, 5);
        Assert.assertEquals(gameConsole.getMatchWinner(), setScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }
}
