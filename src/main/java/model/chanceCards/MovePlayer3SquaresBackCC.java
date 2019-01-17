package model.chanceCards;

import model.ChanceCard;

public class MovePlayer3SquaresBackCC extends ChanceCard {
    private int squaresToMove;

    public MovePlayer3SquaresBackCC(String message, int i) {
        super(message);
        this.squaresToMove = i;

    }
}
