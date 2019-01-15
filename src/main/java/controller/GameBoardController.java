package controller;

import model.GameBoard;
import model.square.Square;
import ui.GUIBoundary;

public class GameBoardController {


    private GameBoard gameBoard;
    private Square currSquare;


    public GameBoardController(GUIBoundary guiBoundary, BankruptController bankruptController){

        gameBoard = new GameBoard(new PropertyController(guiBoundary, bankruptController), new ChanceCardController(guiBoundary));

    }

    public void actOnSquare(PlayerController playerCtrl) {

        int currPosition = playerCtrl.getCurrPlayerPos();
        currSquare = gameBoard.getSquareList()[currPosition];
        currSquare.landedOn(playerCtrl);
    }

}


