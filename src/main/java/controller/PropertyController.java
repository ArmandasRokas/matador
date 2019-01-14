package controller;

import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class PropertyController {

    private GUIBoundary guiB;
    private BankruptController bankruptCtrl;


    public PropertyController(GUIBoundary guiBoundary, BankruptController bankruptCtrl){
        this.guiB = guiBoundary;
        this.bankruptCtrl = bankruptCtrl;

    }

    public void buyProperty(PropertySquare square, PlayerController playerController) {

        boolean answer = false;
        //TODO if player have enough money run line 21. else setCurrentScenario. Do not have enought money to buy square.getSquareName().
        answer = guiB.askToBuyProperty(playerController.getCurrPlayerID(), square.getIndex());


        if(answer){

            int price = square.getBuyPrice();

            playerController.currPlayerMoneyInfluence(-price);
            playerController.addCurrPlayerProperty(square);

            square.setIsOwned(true);
            square.setOwner(playerController.getCurrPlayer());
            square.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " købt " + square.getSquareName());

            guiB.setOwnerOnSquare(playerController.getCurrPlayerID(), square.getIndex(), square.getRentPrice());
            guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
        } else{
            square.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " afviste at købe " + square.getSquareName());
        }


    }

    public void payRent(PropertySquare propertySquare, PlayerController playerController) {

        if(playerController.getCurrPlayerBalance() < propertySquare.getRentPrice()) {
            propertySquare.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke penge nok til at betale renten.");
            bankruptCtrl.handleNegativeBalance(propertySquare, playerController);
        //TODO naviger til pantsætningsside, hvor yderligere valg foretages
        } else {
            playerController.payPlayer(propertySquare.getOwner(), propertySquare.getRentPrice());
            guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
            guiB.updateBalance(propertySquare.getOwner().getPlayerID(), propertySquare.getOwner().getBalance());

            propertySquare.setCurrScenarioForPlayer(playerController.getCurrPlayerName()
                    + " er landet på " + propertySquare.getSquareName() + " som er ejet af " + propertySquare.getOwner() +
                    ". " + playerController.getCurrPlayerName() + " har betalt " + propertySquare.getRentPrice() + "kr til " +
                    propertySquare.getOwner());
        }
        //TODO hvis daværende spiller går fallit med mindre pantsætning- og husværdi skal alt hvad spilleren ejer overgå til ejeren af grunden
    }
}
