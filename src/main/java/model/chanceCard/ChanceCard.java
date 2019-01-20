package model.chanceCard;

import controller.ChanceCardController;
import controller.PlayerController;

public abstract class ChanceCard {
    private String cardText;

    public ChanceCard(String message) {
        this.cardText = message;
    }

    public abstract void pickedCard(ChanceCardController cardCtrl, PlayerController playerCtrl);

    public String getCardText() {
        return cardText;
    }
}
