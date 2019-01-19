package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class BankruptController {
    private GUIBoundary guiB;

    public BankruptController(GUIBoundary guiB){
        this.guiB = guiB;
    }

    public void handleNegativeBalance(PropertySquare propertySquare, PlayerController playerController, PropertyController propertyController){
        transferPropertyToCreditor(playerController, propertySquare.getOwner(), propertyController);
        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har gået fallit. Bye bye. ");
        guiB.removePlayer(playerController.getCurrPlayerPos(), playerController.getCurrPlayerID());
    }

    public void transferPropertyToCreditor(PlayerController playerCtrl, Player owner, PropertyController propertyCtrl) {
            PropertySquare[] currentPlayerProperties = playerCtrl.getCurrPlayerProperties();

            for(PropertySquare square: currentPlayerProperties) {
                if(square != null){
                    square.setOwner(owner);
                }
            }
            playerCtrl.payPlayer(owner, playerCtrl.getCurrPlayerBalance());
            playerCtrl.setCurrPlayerBalance(0);

            guiB.updateBalance(playerCtrl.getCurrPlayerID(), playerCtrl.getCurrPlayerBalance());
            guiB.updateBalance(owner.getPlayerID(), owner.getBalance());

            for(PropertySquare square: currentPlayerProperties) {
                if(square != null){
                    guiB.setOwnerOnSquare(owner.getPlayerID(), square.getIndex(), square.getRentPrice());
                    propertyCtrl.updateSiblingSquaresRentPrice(square);
                }
            }
            playerCtrl.currPlayerGoBankrupt();
    }
}