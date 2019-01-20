package controller;

import model.Cup;
import model.square.property.Company;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class PropertyController {
    private GUIBoundary guiB;
    private BankruptController bankruptCtrl;
    private Cup cup;

    public PropertyController(GUIBoundary guiBoundary, BankruptController bankruptCtrl, Cup cup){
        this.guiB = guiBoundary;
        this.bankruptCtrl = bankruptCtrl;
        this.cup = cup;
    }

    public void handleProperty(PropertySquare propertySquare, PlayerController playerController) {
        if(propertySquare.getOwner() != null && !playerController.getCurrPlayer().equals(propertySquare.getOwner())){ //pay rent.
            this.payRent(propertySquare, playerController);
        } else if (propertySquare.getOwner() != null && playerController.getCurrPlayer().equals(propertySquare.getOwner())){ //owned by current player
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " står på " + propertySquare.toString() +
                    " som " + playerController.getCurrPlayerName() + " ejer selv.");

        } else if(propertySquare.getOwner() == null){ //buy property
            this.buyProperty(propertySquare, playerController);
        }
    }

    public void buyProperty(PropertySquare square, PlayerController playerController) {
        if(playerController.getCurrPlayerBalance() >= square.getBuyPrice()){    //Able to buy property
            boolean answer;
            answer = guiB.askToBuyProperty(playerController.getCurrPlayerID(), square.toString());

            if(answer){ //Buying property
                int price = square.getBuyPrice();
                playerController.currPlayerMoneyInfluence(-price);
                playerController.addCurrPlayerProperty(square);
                playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har købt " + square);

                square.setOwner(playerController.getCurrPlayer());

                guiB.setOwnerOnSquare(playerController.getCurrPlayerID(), square.getIndex(), square.getRentPrice());
                guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
                updateSiblingSquaresRentPrice(square);
            } else{ //Declining to buy property
                playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " afviste at købe " + square);
            }

        } else{ //Not able to buy property
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke nok penge til at købe " + square);
        }
    }

    public void payRent(PropertySquare propertySquare, PlayerController playerController) {
        int rent;

        if (propertySquare instanceof Company) {    //Sets rent for companies with rollscore
            rent = propertySquare.getRentPrice() * cup.getCurrentRollScore();
        } else {    //Sets rent normally
            rent = propertySquare.getRentPrice();
        }

        if(!bankruptCtrl.playerCanPay(playerController, -rent)) {    //Not able to pay rent
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke penge nok til at betale renten.");
            guiB.showCurrScenarioForPlayer(playerController.getCurrPlayerName() + " har ikke penge nok til at betale renten.");
            bankruptCtrl.goBankrupt(propertySquare, playerController);
        } else {    //Able to pay rent
            playerController.payPlayer(propertySquare.getOwner(), rent);
            guiB.updateBalance(playerController.getCurrPlayerID(), playerController.getCurrPlayerBalance());
            guiB.updateBalance(propertySquare.getOwner().getPlayerID(), propertySquare.getOwner().getBalance());
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " er landet på " + propertySquare + " som er ejet af " + propertySquare.getOwner() +
                    ". " + playerController.getCurrPlayerName() + " har betalt " + rent + "kr til " + propertySquare.getOwner());
        }
    }

    public void updateSiblingSquaresRentPrice(PropertySquare propertySquare){
        for (PropertySquare siblingSquare : propertySquare.getSiblingsSquares()) {
            if(siblingSquare.getOwner() != null) {
                guiB.updateRentPrice(siblingSquare.getIndex(), siblingSquare.getRentPrice());
            }
        }
    }
}
