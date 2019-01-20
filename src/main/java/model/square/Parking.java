package model.square;

import controller.PlayerController;
import controller.PropertyController;

public class Parking extends Square {
    public Parking(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {
        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " landet på " + this);
    }
}