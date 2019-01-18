package model.square;

import controller.PlayerController;

public class ToJail extends Square {

    public ToJail(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {

        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " er landet på " + this);
        playerController.setCurrPlayerIsInJail(true);
        playerController.movePlayerToSquare(10, false);



    }
}
