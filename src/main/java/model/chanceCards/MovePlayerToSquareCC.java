package model.chanceCards;

import model.ChanceCard;

public class MovePlayerToSquareCC extends ChanceCard {
    private int destinationIndex;
//    private String destinationName;
    private boolean toPrison;


    public MovePlayerToSquareCC(String message, boolean goingToPrison) {
        super(message);
        this.toPrison = goingToPrison;
    }
}
