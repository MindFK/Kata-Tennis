package org.kata;

import org.kata.play.PlayerActs;
import org.kata.entities.Player;

/**
 * Created by Walid GHARIANI on 10/27/2018-12:35 AM.
 */
public class Main {

    public static void main(String[] args) {
        PlayerActs gameConsole = new PlayerActs();
        gameConsole.joinTheGame(new Player("Player1"));
        gameConsole.joinTheGame(new Player("Player2"));
        gameConsole.startTheSet();
        gameConsole.playASet();
    }
}
