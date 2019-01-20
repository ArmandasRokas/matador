package model.chanceCard;

import controller.ChanceCardController;
import controller.PlayerController;

public class GetOutOfJailCC extends ChanceCard {
    private boolean inDeck;

    public GetOutOfJailCC(String message) {
        super(message);
        this.inDeck = true;
    }

    @Override
    public void pickedCard(ChanceCardController cardCtrl, PlayerController playerCtrl) {
        cardCtrl.handleChanceCard(this, playerCtrl);
    }

    public boolean isInDeck() {
        return inDeck;
    }

    public void setInDeck(boolean inDeck) {
        this.inDeck = inDeck;
    }
}
