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

    public void buyHouse(String squareName) {
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        StreetSquare street = (StreetSquare)squareList[squareIndex];
        street.buyAHouse();
        int numberOfHouses = street.getNumberOfHouses();
        gui.setAHouse(squareIndex, numberOfHouses);
    }
}
