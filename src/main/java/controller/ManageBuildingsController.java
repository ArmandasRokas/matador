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

        if(plCtrl.getCurrPlayerBalance() >= street.getHousePrice() && street.getNumberOfHouses() < 5) {
            plCtrl.currPlayerMoneyInfluence(-street.getHousePrice());
            street.buyAHouse();
            int numberOfHouses = street.getNumberOfHouses();
            gui.setHousing(squareIndex, numberOfHouses);
            gui.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
            plCtrl.setCurrScenarioForPlayer("Tillykke, " + plCtrl.getCurrPlayerName() + "! Du har udvidet " + squareName);
        } else {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har ikke penge nok.");
        }

    }
}
