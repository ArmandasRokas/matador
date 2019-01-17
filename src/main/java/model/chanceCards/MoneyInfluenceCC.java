package model.chanceCards;

import model.ChanceCard;

public class MoneyInfluenceCC extends ChanceCard {
    private int monneyInfluence;


    public MoneyInfluenceCC(String message, int moneyInfluence) {
        super(message);
        this.monneyInfluence = moneyInfluence;
    }
}
