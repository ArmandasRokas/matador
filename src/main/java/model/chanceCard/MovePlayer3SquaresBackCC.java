package model.chanceCard;

import controller.ChanceCardController;
import controller.PlayerController;

public class MovePlayer3SquaresBackCC extends ChanceCard {
    private int squaresToMove;

    public MovePlayer3SquaresBackCC(String message, int i) {
        super(message);
        this.squaresToMove = i;

    }
    @Override
    public void pickedCard(ChanceCardController cardCtrl, PlayerController playerCtrl) {
        cardCtrl.handleChanceCard(this, playerCtrl);
    }
}
