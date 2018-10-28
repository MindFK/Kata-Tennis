package org.kata.services.game;

import org.kata.entities.Player;

import java.util.Random;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:28 PM.
 */
public class AutoPlayer extends Player {
    private static Random random = new Random();
    private static int PossibleBallPosition = 7;
    private boolean lostThePoint = false;


    public static AutoPlayer wrap(Player player) {
        AutoPlayer AutoPlayerService = new AutoPlayer();
        AutoPlayerService.setName(player.getName());
        return AutoPlayerService;
    }

    public boolean hasLostThePoint() {
        return lostThePoint;
    }

    public void setLostThePoint(boolean lostThePoint) {
        this.lostThePoint = lostThePoint;
    }

    public int sendTheService() {
        int shoot = random.nextInt(PossibleBallPosition);
        checkTheShoot(shoot);
        return shoot;
    }

    public void checkTheShoot(int shoot) {
        if (!isAValidShoot(shoot))
            this.lostThePoint = true;
    }

    public static boolean isAValidShoot(int shoot) {
        return shoot >= 1 && shoot <= 5;
    }

    public int sendBack(int position) {
        int playerPosition = random.nextInt(PossibleBallPosition);
        if (playerPosition - position <= random.nextInt(2) + 2)
            return sendTheService();
        this.lostThePoint = true;
        return -1;
    }

}