package controller;

import model.GameBoard;
import model.square.Square;
import ui.GUIBoundary;

public class GameBoardController {


    private GameBoard gameBoard;
    private Square currSquare;


    public GameBoardController(GUIBoundary guiBoundary){

        gameBoard = new GameBoard(new PropertyController(guiBoundary), new ChanceCardController(guiBoundary));


    }

    public void actOnSquare(PlayerController playerCtrl) {

        int currPosition = playerCtrl.getCurrPlayerPos();
        currSquare = gameBoard.getSquareList()[currPosition];
        currSquare.landedOn(playerCtrl);
    }

    public String getCurrSquareToString(){
        return currSquare.toString();
    }
}


