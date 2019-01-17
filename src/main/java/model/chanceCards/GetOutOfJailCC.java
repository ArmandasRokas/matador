package model.chanceCards;

import model.ChanceCard;

public class GetOutOfJailCC extends ChanceCard {
    private boolean inDeck;

    public GetOutOfJailCC(String message) {
        super(message);
        this.inDeck = true;
    }
}
