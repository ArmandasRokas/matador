package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

import java.util.ArrayList;

public class BankruptController {
    private GUIBoundary guiB;
    private PropertyController propertyCtrl;

    public BankruptController(GUIBoundary guiB){
        this.guiB = guiB;
    }

    public boolean playerCanPay(PlayerController playerCtrl, int moneyInfluence) {
        return playerCtrl.getCurrPlayerBalance() > -moneyInfluence;
    }

    public void setPropertyCtrl(PropertyController propertyCtrl){
        this.propertyCtrl = propertyCtrl;
    }

    public void goBankrupt(PropertySquare propertySquare, PlayerController playerCtrl){ //Another player is creditor
        transferPropertyToCreditor(playerCtrl, propertySquare.getOwner(), propertyCtrl);
        playerCtrl.setCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " er gået fallit og sat ud af spillet ");
        guiB.removePlayerByBankrupt(playerCtrl.getCurrPlayerPos(), playerCtrl.getCurrPlayerID());
    }

    public void goBankrupt(PlayerController playerCtrl) { //Bank is creditor
        transferPropertyToCreditor(playerCtrl, propertyCtrl);
        playerCtrl.setCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " er gået fallit og sat ud af spillet ");
        guiB.removePlayerByBankrupt(playerCtrl.getCurrPlayerPos(), playerCtrl.getCurrPlayerID());
    }

    private void transferPropertyToCreditor(PlayerController playerCtrl, PropertyController propertyCtrl) { //Bank is creditor
            ArrayList<PropertySquare> currentPlayerProperties = playerCtrl.getCurrPlayerProperties();

        for(PropertySquare square: currentPlayerProperties) {
            square.setOwner(null);
        }
        playerCtrl.setCurrPlayerBalance(0);
        guiB.updateBalance(playerCtrl.getCurrPlayerID(), playerCtrl.getCurrPlayerBalance());

        for(PropertySquare square: currentPlayerProperties) {
            guiB.setOwnerOnSquare(square.getIndex(), square.getRentPrice());
            propertyCtrl.updateSiblingSquaresRentPrice(square);
        }
        playerCtrl.currPlayerSetBankrupt();
    }

    private void transferPropertyToCreditor(PlayerController playerCtrl, Player owner, PropertyController propertyCtrl) {    //Another player is creditor
        ArrayList<PropertySquare> currentPlayerProperties = playerCtrl.getCurrPlayerProperties();

        for(PropertySquare square: currentPlayerProperties) {
                square.setOwner(owner);
        }
        playerCtrl.payPlayer(owner, playerCtrl.getCurrPlayerBalance());
        playerCtrl.setCurrPlayerBalance(0);
        guiB.updateBalance(playerCtrl.getCurrPlayerID(), playerCtrl.getCurrPlayerBalance());
        guiB.updateBalance(owner.getPlayerID(), owner.getBalance());

        for(PropertySquare square: currentPlayerProperties) {
            guiB.setOwnerOnSquare(owner.getPlayerID(), square.getIndex(), square.getRentPrice());
            propertyCtrl.updateSiblingSquaresRentPrice(square);
        }
        playerCtrl.currPlayerSetBankrupt();
    }
}