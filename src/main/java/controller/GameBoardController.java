package controller;

import model.GameBoard;
import model.square.Square;
import ui.GUIBoundary;

public class GameBoardController {


    private GameBoard gameBoard;
    private Square currSquare;
    private GUIBoundary guiBoundary;
    private BankruptController bankruptCtrl; //FixMe Skal fjernes? Test ved at kører

    public GameBoardController(GUIBoundary guiBoundary){
//    public GameBoardController(GUIBoundary guiBoundary, BankruptController bankruptCtrl){

        gameBoard = new GameBoard();
        this.guiBoundary = guiBoundary;
//        this.bankruptCtrl = bankruptCtrl;
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


