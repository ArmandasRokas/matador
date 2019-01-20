package model.chanceCard;

import controller.ChanceCardController;
import controller.PlayerController;

public class MoneyInfluenceCC extends ChanceCard {
    private int moneyInfluence;

    public MoneyInfluenceCC(String message, int moneyInfluence) {
        super(message);
        this.moneyInfluence = moneyInfluence;
    }
    @Override
    public void pickedCard(ChanceCardController cardCtrl, PlayerController playerCtrl) {
        cardCtrl.handleChanceCard(this, playerCtrl);
    }

    public int getMoneyInfluence() {
        return moneyInfluence;
    }
}
