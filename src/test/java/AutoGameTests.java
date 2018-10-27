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
        AutoGameController gameConsole = new AutoGameController();
        gameConsole.joinTheGame(p1);
        gameConsole.joinTheGame(p2);
        gameConsole.startTheSet();
        gameConsole.playASet();
        Assert.assertNotNull(gameConsole.getSetWinner());
        Assert.assertEquals(gameConsole.getSetWinnerScore(), 40);
    }

}
