package model.square;

import controller.ChanceCardController;
import controller.PlayerController;

public class ChanceSquare extends Square {
    public ChanceSquare(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {

        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " landet på " + this);

    }
}
