package model.square;

import controller.PlayerController;
import controller.PropertyController;

public class Jail extends Square {
    public Jail(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {
        if(playerController.getIsCurrPlayerInJail()) {
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " er i Fængsel");
        } else {
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " er " + this);
        }
    }
}