package controller;

import model.GameBoard;
import model.square.Square;
import model.square.property.StreetSquare;
import ui.GUIBoundary;

public class ManageBuildingsController {
    private Square[] squareList;
    private GameBoard gameBoard;
    private GUIBoundary gui;

    public ManageBuildingsController(GUIBoundary gui, GameBoard gameBoard) {
        this.squareList = gameBoard.getSquareList();
        this.gameBoard = gameBoard;
        this.gui = gui;
    }

    public void buyHouse(PlayerController plCtrl, String squareName) {
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        StreetSquare street = (StreetSquare)squareList[squareIndex];

        if(street.getNumberOfHouses() == 5) {
            plCtrl.setCurrScenarioForPlayer("Der er allerede ét hotel på denne grund!");
        } else if(!street.isBuildingEvenly()) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " kan ikke udvide denne grund før de andre er på samme niveau");
        }
        else if(plCtrl.getCurrPlayerBalance() >= street.getHousePrice() && street.getNumberOfHouses() < 5) {
            plCtrl.currPlayerMoneyInfluence(-street.getHousePrice());
            street.buyAHouse();
            int numberOfHouses = street.getNumberOfHouses();
            gui.setHousing(squareIndex, numberOfHouses);
            gui.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
            gui.updateRentPrice(squareIndex, street.getRentPrice());
            plCtrl.setCurrScenarioForPlayer("Tillykke, " + plCtrl.getCurrPlayerName() + "! Du har udvidet " + squareName);
        } else if(plCtrl.getCurrPlayerBalance() < street.getHousePrice()) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har ikke penge nok.");
        } else {
            plCtrl.setCurrScenarioForPlayer("Der er sket en fejl, kontakt tekniker");
        }

    }
}
