package org.kata.play;

import org.kata.entities.Game;
import org.kata.game.AutoTennisGame;

/**
 * Created by Walid GHARIANI on 10/27/2018-3:19 AM.
 */
public class PlayerActs extends Acts {

    public PlayerActs(Game game){
        this.game = new AutoTennisGame(game);
    }
    public PlayerActs(){//players will join the game later
        this.game = new AutoTennisGame(new Game());
    }

    public void playASet(){
        game.checkSetIsStarted();
        ((AutoTennisGame)game).playASet();
    }
}
