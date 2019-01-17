package model.chanceCards;

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
}
