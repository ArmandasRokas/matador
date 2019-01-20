package controller;

import model.Player;
import model.square.ChanceSquare;
import model.square.IncomeTax;
import model.square.LuxuryTax;
import model.square.ToJail;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

import java.awt.Color;
import java.util.ArrayList;

public class PlayerController {
    private Player[] playerList;
    private GameRules gameRules;
    private GUIBoundary guiB;
    private Player currPlayer;
    private String currScenarioForPlayer = "[NO SCENARIO SET]";
    private PropertyController propertyCtrl;
    private ChanceCardController chanceCardCtrl;
    private GameBoardController gameBoardCtrl;
    private BankruptController bankruptCtrl;
    private int currPlayerExtraTurnCount;

    public PlayerController(GUIBoundary guiB, GameRules gameRules, int numberOfPlayers, PropertyController propertyCtrl,
                            ChanceCardController chanceCardCtrl, GameBoardController gameBoardCtrl, BankruptController bankruptCtrl) {
        this.propertyCtrl = propertyCtrl;
        this.guiB = guiB;
        this.gameRules = gameRules;
        this.chanceCardCtrl = chanceCardCtrl;
        this.gameBoardCtrl = gameBoardCtrl;
        this.bankruptCtrl = bankruptCtrl;
        this.currPlayerExtraTurnCount = 0;

        playerList = new Player[numberOfPlayers];
    }

    public void createPlayers() {
        String[] names = guiB.askForNames(playerList.length);
        Color[] carColors = gameRules.getColors();

        for(int i = 0; i < playerList.length; i++){
            playerList[i] = new Player(i, names[i], gameRules.getStartBalance());
            guiB.setupPlayer(playerList[i].getPlayerID(), playerList[i].getName(), playerList[i].getBalance(), carColors[i]);
        }

        currPlayer = playerList[0];
    }

    public void movePlayer(int rollScore, boolean canPassStart) {
        int currPosition = currPlayer.getCurrentPosition();
        int newPosition = (rollScore + currPosition) % 40;

        if(newPosition < 0) {
            newPosition = 40 + newPosition;
        }
        currPlayer.setPosition(newPosition);
        guiB.movePlayer(currPosition, newPosition,currPlayer.getPlayerID());

        if(canPassStart && gameRules.passStart(currPosition, newPosition)) {
            giveStartIncome();
        }
    }

    public void movePlayerToSquare(int newPosition, boolean goingToPrison){
        int currPosition = currPlayer.getCurrentPosition();
        currPlayer.setPosition(newPosition);
        guiB.movePlayer(currPosition, newPosition, getCurrPlayerID());

        if(!goingToPrison && gameRules.passStart(currPosition, newPosition)) {
            giveStartIncome();
        }
    }

    private void giveStartIncome() {
        currPlayerMoneyInfluence(200);
        guiB.updateBalance(currPlayer.getPlayerID(), currPlayer.getBalance());
    }

    public void setCurrPlayerToJail(){
        guiB.informPlayerGoingToJail(getCurrPlayerID());
        this.setCurrPlayerIsInJail(true);
        this.movePlayerToSquare(10, true);
    }

    public boolean checkForSpeeding(){
        return currPlayerExtraTurnCount == 3;
    }

    public void changePlayer() {
        do{
            int currID = currPlayer.getPlayerID();
            currID = (currID + 1) % playerList.length;
            currPlayer = playerList[currID];
        } while (currPlayer.isBankrupt());

        resetCurrPlayerExtraTurnCount();
    }

    public void currPlayerMoneyInfluence(int cash) {
        currPlayer.moneyInfluence(cash);
        guiB.updateBalance(currPlayer.getPlayerID(), currPlayer.getBalance());
    }

    public Player[] getPlayerList() {
        return playerList;
    }

    public int getCurrPlayerPos() {
        return currPlayer.getCurrentPosition();
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public String getCurrPlayerName(){
        return currPlayer.getName();
    }

    public void addCurrPlayerProperty(PropertySquare square) {
        currPlayer.addProperty(square);
    }

    public int getCurrPlayerID(){
        return currPlayer.getPlayerID();
    }

    public void setCurrPlayerBalance(int balance) {
        currPlayer.setBalance(balance);
    }
    public int getCurrPlayerBalance(){
        return currPlayer.getBalance();
    }

    public ArrayList<PropertySquare> getCurrPlayerProperties(){
        return currPlayer.getProperties();
    }

    public void payPlayer(Player propertyOwner, int cash) {
        currPlayerMoneyInfluence(-cash);

        if(propertyOwner != null){
            propertyOwner.moneyInfluence(cash);
        }
    }

    public void currPlayerSetBankrupt() {
        this.currPlayer.setBankrupt();
    }

    public void setCurrScenarioForPlayer(String currScenario) {
        this.currScenarioForPlayer = currScenario;
    }
    public String getCurrScenarioForPlayer(){
        return currScenarioForPlayer;
    }

    public void setCurrPlayerIsInJail(boolean isInJail) {
        if(!isInJail) {
            currPlayer.resetTurnsTakenInJail();
        }
        currPlayer.setIsCurrPlayerInJail(isInJail);
    }

    public boolean getIsCurrPlayerInJail() {
        return currPlayer.getIsCurrPlayerInJail();
    }
    public int getCurrPlayerExtraTurnCount(){
        return currPlayerExtraTurnCount;
    }

    public void addOneCurrPlayerExtraTurnCount(){
        currPlayerExtraTurnCount++;
    }
    public void resetCurrPlayerExtraTurnCount(){
        currPlayerExtraTurnCount = 0;
    }

    public void giveOutOfJailCard() {
        currPlayer.addOutOfJailCard();
    }
    public boolean hasGetOutOfJailCard() {
        return currPlayer.getGetOutOfJailCards() > 0;
    }
    public void useGetOutOfJailCard() {
        currPlayer.useGetOutOfJailCard();
    }

    public int getTurnsInJail() {
        return currPlayer.getTurnsTakenInJail();
    }

    public boolean payToGetOutOfJail() {
        int bail = gameRules.getGetOutOfJailBail();
        boolean res;

        if(bankruptCtrl.playerCanPay(this, -bail)){
            currPlayerMoneyInfluence(-bail);
            guiB.updateBalance(getCurrPlayerID(), getCurrPlayerBalance());
            res = true;
        } else {
            guiB.showCurrScenarioForPlayer(getCurrPlayerName() + " har ikke penge nok til at betale 50kr til Fængsel.");
            bankruptCtrl.goBankrupt(this);
            guiB.showCurrScenarioForPlayer(this.getCurrPlayerName() + " er gået fallit og sat ud af spillet");
            res = false;
        }
        return res;
    }

    //Handling of squares
    public void handleSquare(PropertySquare propertySquare){
        propertyCtrl.handleProperty(propertySquare, this);
    }
    public void handleSquare(ChanceSquare chanceSquare) {
        chanceCardCtrl.handleChanceCards(this);
    }
    public void handleSquare(IncomeTax incomeTax){
        gameBoardCtrl.payIncomeTax(this, bankruptCtrl, gameRules);
    }
    public void handleSquare(LuxuryTax luxuryTax){
        gameBoardCtrl.payLuxuryTax(this, bankruptCtrl, gameRules);
    }

    public void handleSquare(ToJail toJail){
        gameBoardCtrl.toJail(this);
    }
}