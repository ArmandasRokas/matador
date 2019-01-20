package model.square;

import controller.PlayerController;

public class IncomeTax extends Square {
    public IncomeTax(String name, int index) {
        super(name, index);
    }

    @Override
    public void landedOn(PlayerController playerController) {
        playerController.handleSquare(this);
    }
}
