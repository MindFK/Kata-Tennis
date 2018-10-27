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
    private static Score setScore;
    private static GameScoreByPlayerIndex gameScoreByPlayerIndex;

    private static int default_winner_player_index = 1;

    @Before
    public void start() {
        gameConsole.initPlayersAndScores();
        gameConsole.joinTheGame(p1);
        gameConsole.joinTheGame(p2);
        game = gameConsole.getGameModel();
        setScore = gameConsole.getSetScore();
        gameScore = gameConsole.getGameScore();
        gameScoreByPlayerIndex = gameConsole.getGameScoreByIndex();
    }

    @Test
    public void testPlayTowGames() {
        gameConsole.startTheSet();
        playAGame(default_winner_player_index, 1, 0);
        playAGame(default_winner_player_index, 2, 0);
    }

    @Test
    public void testGameWinner() {
        gameConsole.startTheSet();
        playAGame();
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
        playAGame(default_winner_player_index, 1, 0);
        playAGame(default_winner_player_index, 2, 0);
        playAGame(default_winner_player_index, 3, 0);
        playAGame(default_winner_player_index, 4, 0);
        playAGame(default_winner_player_index, 5, 0);
        playAGame(default_winner_player_index, 6, 0);

        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));
    }

    @Test
    public void testPointIncrementation() {
        gameConsole.startTheSet();
        gameConsole.winAPoint(default_winner_player_index);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(default_winner_player_index), 15);
    }

    private void playAGame() {
        playAGame(default_winner_player_index, 1, 0);
    }

    private void playAGame(int winnerIndex, int expectedWinnerSetScore, int expectedLooserSetScore) {
        gameConsole.startTheSet();
        int looserIndex = 1 - winnerIndex;
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 0);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 15);
        gameConsole.winAPoint(looserIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 15);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 30);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);
        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(winnerIndex), 40);
        Assert.assertEquals(gameScoreByPlayerIndex.getScore(looserIndex), 30);

        gameConsole.winAPoint(winnerIndex);
        Assert.assertEquals(gameConsole.getSetWinner(), gameScoreByPlayerIndex.getPlayer(default_winner_player_index));

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
