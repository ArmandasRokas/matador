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

        if(playerController.getCurrPlayerBalance() >= square.getBuyPrice()){

            boolean answer;
            answer = guiB.askToBuyProperty(playerController.getCurrPlayerID(), square.getIndex());
            if(answer){

                int price = square.getBuyPrice();
                playerController.currPlayerMoneyInfluence(-price);
                playerController.addCurrPlayerProperty(square);
                playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har købt " + square);

                square.setOwner(playerController.getCurrPlayer());

                guiB.setOwnerOnSquare(playerController.getCurrPlayerID(), square.getIndex(), square.getRentPrice());
                guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
            } else{
                playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " afviste at købe " + square);
            }
        } else{
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke nok penge til at købe " + square);
        }
    }

    public void payRent(PropertySquare propertySquare, PlayerController playerController) {

        if(playerController.getCurrPlayerBalance() < propertySquare.getRentPrice()) {
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke penge nok til at betale renten.");
            bankruptCtrl.handleNegativeBalance(propertySquare, playerController);
        //TODO naviger til pantsætningsside, hvor yderligere valg foretages
        } else {
            playerController.payPlayer(propertySquare.getOwner(), propertySquare.getRentPrice());
            guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
            guiB.updateBalance(propertySquare.getOwner().getPlayerID(), propertySquare.getOwner().getBalance());

            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName()
                    + " er landet på " + propertySquare + " som er ejet af " + propertySquare.getOwner() +
                    ". " + playerController.getCurrPlayerName() + " har betalt " + propertySquare.getRentPrice() + "kr til " +
                    propertySquare.getOwner());
        }
        //TODO hvis daværende spiller går fallit med mindre pantsætning- og husværdi skal alt hvad spilleren ejer overgå til ejeren af grunden
    }
}
