package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class BankruptController {

    private GUIBoundary guiBoundary;

    public BankruptController(GUIBoundary guiBoundary){
        this.guiBoundary = guiBoundary;
    }

    public void handleNegativeBalance(PropertySquare propertySquare, PlayerController playerController){


            // vis muligheder til at sælge huse
            // vis mulighder til at pansætte grund


        // hvis ingen mulighder


            // setCurrentPlayerScenario(" du er gået falit.
            // boolean isBankrupt is true
            // slet bilen fra spillerpladen.

        transferPropertyToCreditor(playerController, propertySquare.getOwner());
        playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName()
        + " har gået fallit. Bye bye. ");
        guiBoundary.removePlayer(playerController.getCurrPlayerPos(), playerController.getCurrPlayerID());


    }

    public void transferPropertyToCreditor(PlayerController playerController, Player owner) {
            PropertySquare[] currentPlayerProperties = playerController.getCurrPlayerProperties();

            for(PropertySquare square: currentPlayerProperties) {

                if(square != null){
                    square.setOwner(owner);
                }
            }
            playerController.payPlayer(owner, playerController.getCurrPlayerBalance());
            playerController.setCurrPlayerBalance(0);
            //TODO chancekort gives også til banken

            guiBoundary.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
            guiBoundary.updateBalance(owner.getPlayerID(), owner.getBalance());

            playerController.currPlayerGoBankrupt();
    }

}