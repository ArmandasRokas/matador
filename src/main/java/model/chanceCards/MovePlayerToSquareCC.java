package model.chanceCards;

import controller.ChanceCardController;
import controller.PlayerController;

public class MovePlayerToSquareCC extends ChanceCard {
    private int destinationIndex;
//    private String destinationName;
    private boolean toPrison;
    private int squareIndex;


    public MovePlayerToSquareCC(String message, boolean goingToPrison, int squareIndex) {
        super(message);
        this.toPrison = goingToPrison;
        this.squareIndex = squareIndex;
    }
    @Override
    public void pickedCard(ChanceCardController cardCtrl, PlayerController playerCtrl) {
        cardCtrl.handleChanceCard(this, playerCtrl);
    }
}
