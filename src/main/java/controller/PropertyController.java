package controller;

import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class PropertyController {

    private GUIBoundary guiB;


    public PropertyController(GUIBoundary guiBoundary){

        this.guiB = guiBoundary;

    }

    public void buyProperty(PropertySquare square, PlayerController playerController) {

        boolean answer = false;
        //TODO if player have enough money run line 21. else setCurrentScenario. Do not have enought money to buy square.getSquareName().
        answer = guiB.askToBuyProperty(playerController.getCurrPlayerID(), square.getIndex());


        if(answer){

            int price = square.getBuyPrice();

            playerController.moneyInfluence(-price);
            playerController.addCurrPlayerProperty(square);

            square.setIsOwned(true);
            square.setOwner(playerController.getCurrPlayer());
            square.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " købt " + square.getSquareName());

            guiB.setOwnerOnSquare(playerController.getCurrPlayerID(), square.getIndex());
            guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
        }


    }
}
