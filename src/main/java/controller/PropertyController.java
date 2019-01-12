package controller;

import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class PropertyController {

    private GUIBoundary guiB;


    public PropertyController(GUIBoundary guiBoundary){

        this.guiB = guiBoundary;

    }

    public void buyProperty(PropertySquare square, PlayerController playerController) {

        boolean answer = guiB.askToBuyProperty(playerController.getCurrPlayerName(), square.getSquareName());


        if(answer){

            int price = square.getBuyPrice();

            playerController.getCurrPlayer().moneyInfluence(-price);
            square.setOwner(playerController.getCurrPlayer());
            square.setIsOwned(true);

            playerController.setProperty(square);
            square.setCurrScenarioForPlayer(p.getName() + " købt " + square.getSquareName());
        }


    }
}
