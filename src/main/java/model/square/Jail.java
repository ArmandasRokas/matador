package model.square;

import controller.PlayerController;
import controller.PropertyController;

public class Jail extends Square {
    public Jail(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {

        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " landet på " + this);

    }
}
