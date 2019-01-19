package model.square;

import controller.PlayerController;

public class  StartSquare extends Square {
    public StartSquare(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {
        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " landet på " + this);
    }
}
