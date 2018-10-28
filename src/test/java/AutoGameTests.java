import org.junit.Assert;
import org.junit.Test;
import org.kata.entities.Player;
import org.kata.controllers.AutoGameController;

/**
 * Created by Walid GHARIANI on 10/27/2018-3:28 AM.
 */
public class AutoGameTests {
    private Player p1 = new Player("Roger Federer");
    private Player p2 = new Player("Novak Djokovic");

    @Test
    public void testWinnerScore() {
        AutoGameController gameController = new AutoGameController();
        gameController.joinTheGame(p1);
        gameController.joinTheGame(p2);
        gameController.startTheSet();
        gameController.playASet();
        Assert.assertNotNull(gameController.getSetWinner());
        Assert.assertEquals(gameController.getSetWinnerScore(), 40);
    }

    @Test
    public void testAutoPlayAMatch() {
        AutoGameController gameController = new AutoGameController();
        gameController.joinTheGame(p1);
        gameController.joinTheGame(p2);
        gameController.startTheMatch();
        gameController.playAMatch();
        Assert.assertNotNull(gameController.getMatchWinner());
        Assert.assertTrue(gameController.getMatchWinnerScore() - gameController.getMatchLooserScore() >= 2);
        Assert.assertTrue(gameController.getMatchWinnerScore() >= 6);
    }

}
