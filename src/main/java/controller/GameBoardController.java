package controller;

import model.GameBoard;
import model.square.Square;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class GameBoardController {


    private GameBoard gameBoard;
    private Square currSquare;
    private GUIBoundary guiB;
    private BankruptController bankruptCtrl; //FixMe Skal fjernes? Test ved at kører

    public GameBoardController(GUIBoundary guiB){
//    public GameBoardController(GUIBoundary guiB, BankruptController bankruptCtrl){

        gameBoard = new GameBoard();
        this.guiB = guiB;
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
            guiB.setupGUIFields(i, name);
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void payIncomeTax(PlayerController playerCtrl) {

        double sumFromHisProperties = 0;
        for(PropertySquare propertySquare: playerCtrl.getCurrPlayerProperties()){
            if (propertySquare != null) {
                sumFromHisProperties += propertySquare.getBuyPrice();
            }
        }
        double payPercent = (((double)playerCtrl.getCurrPlayerBalance()+ sumFromHisProperties) / 100) * 10;
        int incomeTaxAnswer  = guiB.incomeTax(playerCtrl);
        switch (incomeTaxAnswer){
            case 0:
                playerCtrl.currPlayerMoneyInfluence((int) -payPercent);
                playerCtrl.setCurrScenarioForPlayer(payPercent + " er 10% af " + playerCtrl.getCurrPlayerName() + "'s værdi");
                guiB.showCurrScenarioForPlayer(playerCtrl.getCurrScenarioForPlayer());
                break;
            case 1:
                playerCtrl.setCurrScenarioForPlayer("Du har valgt at betale 200kr");
                playerCtrl.currPlayerMoneyInfluence(-200);
                break;
        }

    }
}


