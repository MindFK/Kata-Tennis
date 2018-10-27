package org.kata.game;

import org.kata.entities.Player;

import java.util.Random;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:28 PM.
 */
public class PlayerService extends Player {
    private static Random random = new Random();
    private static int PossibleBallPosition = 7;
    private boolean lostThePoint = false;

    public static PlayerService wrap(Player player) {
        PlayerService PlayerService = new PlayerService();
        PlayerService.setName(player.getName());
        return PlayerService;
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
        int myposition = random.nextInt(PossibleBallPosition);
        if (myposition - position <= random.nextInt(2) + 2)
            return sendTheService();
        this.lostThePoint = true;
        return -1;
    }
}