package controller;

import model.GameBoard;
import model.square.Square;
import ui.GUIBoundary;

public class GameBoardController {


    private GameBoard gameBoard;
    private Square currSquare;
    private GUIBoundary guiBoundary;


    public GameBoardController(GUIBoundary guiBoundary){

        gameBoard = new GameBoard();
        this.guiBoundary = guiBoundary;
        setupGUISquareNames();
    }

    public void actOnSquare(PlayerController playerCtrl) {

        int currPosition = playerCtrl.getCurrPlayerPos();
        currSquare = gameBoard.getSquareList()[currPosition];
        currSquare.landedOn(playerCtrl);
    }

    public void setupGUISquareNames(){
        for(int i = 0; i < gameBoard.getSquareList().length; i++){
            String name = gameBoard.getSquareList()[i].getSquareName();
            guiBoundary.setupGUIFields(i, name);
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}


