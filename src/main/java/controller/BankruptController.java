package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

import java.util.ArrayList;

public class BankruptController {
    private GUIBoundary guiB;

    public BankruptController(GUIBoundary guiB){
        this.guiB = guiB;
    }

    public boolean playerBalanceGoingNegative(PlayerController playerCtrl, int moneyInfluence) {
        return playerCtrl.getCurrPlayerBalance() > -moneyInfluence;
    }

    public void goBankrupt(PropertySquare propertySquare, PlayerController playerCtrl, PropertyController propertyCtrl){
        transferPropertyToCreditor(playerCtrl, propertySquare.getOwner(), propertyCtrl);
        playerCtrl.setCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " er gået fallit og sat ud af spillet ");
        guiB.removePlayerByBankrupt(playerCtrl.getCurrPlayerPos(), playerCtrl.getCurrPlayerID());
    }

    public void goBankrupt(PlayerController playerCtrl, PropertyController propertyCtrl) {
        transferPropertyToCreditor(playerCtrl, propertyCtrl);
        playerCtrl.setCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " er gået fallit og sat ud af spillet");
        guiB.removePlayerByBankrupt(playerCtrl.getCurrPlayerPos(), playerCtrl.getCurrPlayerID());
    }

    private void transferPropertyToCreditor(PlayerController playerCtrl, PropertyController propertyCtrl) {
        ArrayList<PropertySquare> currentPlayerProperties = playerCtrl.getCurrPlayerProperties();

        for(PropertySquare square: currentPlayerProperties) {
//                if(square != null){
            square.setOwner(null);
//                }
        }

        playerCtrl.setCurrPlayerBalance(0);
        guiB.updateBalance(playerCtrl.getCurrPlayerID(), playerCtrl.getCurrPlayerBalance());

        for(PropertySquare square: currentPlayerProperties) {
//                if(square != null){
            guiB.setOwnerOnSquare(square.getIndex(), square.getRentPrice());
            propertyCtrl.updateSiblingSquaresRentPrice(square);
//                }
        }
        playerCtrl.currPlayerSetBankrupt();
    }

    public void transferPropertyToCreditor(PlayerController playerCtrl, Player owner, PropertyController propertyCtrl) {
            ArrayList<PropertySquare> currentPlayerProperties = playerCtrl.getCurrPlayerProperties();

            for(PropertySquare square: currentPlayerProperties) {
//                if(square != null){
                    square.setOwner(owner);
//                }
            }
            playerCtrl.payPlayer(owner, playerCtrl.getCurrPlayerBalance());
            playerCtrl.setCurrPlayerBalance(0);
            guiB.updateBalance(playerCtrl.getCurrPlayerID(), playerCtrl.getCurrPlayerBalance());
            guiB.updateBalance(owner.getPlayerID(), owner.getBalance());

            for(PropertySquare square: currentPlayerProperties) {
//                if(square != null){
                guiB.setOwnerOnSquare(owner.getPlayerID(), square.getIndex(), square.getRentPrice());
                propertyCtrl.updateSiblingSquaresRentPrice(square);
//                }
            }
            playerCtrl.currPlayerSetBankrupt();


    }
}