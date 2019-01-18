package model.square;

import controller.PlayerController;

public class LuxuryTax extends Square {
    public LuxuryTax(String name, int index) {
        super(name,index);
    }

    @Override
    public void landedOn(PlayerController playerController) {

        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " landet på " + this);
        playerController.currPlayerMoneyInfluence(-100);
    }
}
