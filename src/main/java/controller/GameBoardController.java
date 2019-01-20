package controller;

import model.GameBoard;
import model.square.Square;
import model.square.property.PropertySquare;
import model.square.property.StreetSquare;
import ui.GUIBoundary;

public class GameBoardController {
    private GameBoard gameBoard;
    private Square currSquare;
    private GUIBoundary guiB;

    public GameBoardController(GUIBoundary guiB){
        gameBoard = new GameBoard();
        this.guiB = guiB;
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
            int housePrice = 0;
            if(gameBoard.getSquareList()[i] instanceof StreetSquare) {
                StreetSquare streetSquare = (StreetSquare)gameBoard.getSquareList()[i];
                housePrice = streetSquare.getHousePrice();
            }
            guiB.setupGUIFields(i, name, housePrice);
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void payIncomeTax(PlayerController playerCtrl, BankruptController bankruptCtrl, PropertyController propertyCtrl) {
        double sumFromHisProperties = 0;

        for(PropertySquare propertySquare: playerCtrl.getCurrPlayerProperties()){
            sumFromHisProperties += propertySquare.getBuyPrice();
        }
        double payPercent = (((double)playerCtrl.getCurrPlayerBalance()+ sumFromHisProperties) / 100) * 10;
        int incomeTaxAnswer  = guiB.incomeTax(playerCtrl);

        switch (incomeTaxAnswer){
            case 0:
                if(bankruptCtrl.playerCanPay(playerCtrl, (int) -payPercent)) {
                    playerCtrl.currPlayerMoneyInfluence((int) -payPercent);
                    playerCtrl.setCurrScenarioForPlayer(payPercent + " er 10% af " + playerCtrl.getCurrPlayerName() + "'s værdi");
                } else {
                    guiB.showCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " har ikke penge nok til at betale " + (int) -payPercent + "kr til indkomstskatten.");
                    bankruptCtrl.goBankrupt(playerCtrl);
                }
                break;
            case 1:
                playerCtrl.setCurrScenarioForPlayer("Du har valgt at betale 200kr");
                if(bankruptCtrl.playerCanPay(playerCtrl, -200)) {
                    playerCtrl.currPlayerMoneyInfluence(-200);
                } else {
                    guiB.showCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " har ikke penge nok til at betale 200kr til indkomstskatten.");
                    bankruptCtrl.goBankrupt(playerCtrl);
                }
                break;
        }
    }
}


