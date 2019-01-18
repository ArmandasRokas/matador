package controller;

import model.Player;
import model.square.ChanceSquare;
import model.square.property.PropertySquare;
import model.square.property.StreetSquare;
import ui.GUIBoundary;

import java.awt.Color;

public class PlayerController {
    private Player[] playerList;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Player currPlayer;
    private String currScenarioForPlayer = "[NO SCENARIO SET]";
    private PropertyController propertyCtrl;
    private int turnsTakenInJail;
    private ChanceCardController chanceCardCtrl;
    private int outOfJailCards;
    private GameBoardController gameBoardCtrl;
    private int currPlayerExtraTurnCount;

    public PlayerController(GUIBoundary guiB, GameLogic gL, int numberOfPlayers, PropertyController propertyCtrl,
                            ChanceCardController chanceCardCtrl, GameBoardController gameBoardCtrl) {
        this.propertyCtrl = propertyCtrl;
        this.guiB = guiB;
        this.gL = gL;
        this.turnsTakenInJail = 0;
        this.chanceCardCtrl = chanceCardCtrl;
        this.outOfJailCards = 0;
        this.gameBoardCtrl = gameBoardCtrl;
        this.currPlayerExtraTurnCount = 0;

        playerList = new Player[numberOfPlayers];
    }

    public void createPlayers() {
        String[] names = guiB.askForNames(playerList.length);
        Color[] carColors = gL.getColors();

        for(int i = 0; i < playerList.length; i++){
            playerList[i] = new Player(i, names[i], gL.getStartBalance());
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

        if(canPassStart && gL.passStart(currPosition, newPosition)) {
            havePassedStart();
        }
    }

    public void movePlayerToSquare(int newPosition, boolean canPassStart){
        int currPosition = currPlayer.getCurrentPosition();
        currPlayer.setPosition(newPosition);
        guiB.movePlayer(currPosition, newPosition, getCurrPlayerID());

        if(canPassStart && gL.passStart(currPosition, newPosition)) {
            havePassedStart();
        }
    }

    private void havePassedStart() {
        currPlayerMoneyInfluence(200);
        guiB.updateBalance(currPlayer.getPlayerID(), currPlayer.getBalance());
    }


    public void setCurrPlayerToJail(){
        guiB.informPlayerGoingToJail(getCurrPlayerID());
        this.setCurrPlayerIsInJail(true);
        this.movePlayerToSquare(10, false);
    }

    public boolean checkForSpeeding(){
        return currPlayerExtraTurnCount == 3;
    }

    public void changePlayer() {
            do{
                int currID = currPlayer.getPlayerID();
                currID = (currID + 1) % playerList.length;
                currPlayer = playerList[currID];
            } while (currPlayer.getBankrupt());
    }

    public void currPlayerMoneyInfluence(int cash) {
        currPlayer.moneyInfluence(cash);
        guiB.updateBalance(currPlayer.getPlayerID(), currPlayer.getBalance()); //FixMe var et quickfix for chanceCards (se Issue #4)
    }


    public Player[] getPlayerList() {
        return playerList;
    }

    public int getCurrPlayerPos() {
        int currPos = currPlayer.getCurrentPosition();
        return currPos;
    }

    public String getCurrPlayerName(){
        return currPlayer.getName();
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void addCurrPlayerProperty(PropertySquare square) {
        currPlayer.addProperty(square);
    }

    public int getCurrPlayerID(){
        return currPlayer.getPlayerID();
    }

    public int getCurrPlayerBalance(){
        return currPlayer.getBalance();
    }

    public PropertySquare[] getCurrPlayerProperties(){
        return currPlayer.getProperties();
    }

    public void payPlayer(Player propertyOwner, int cash) {
        currPlayerMoneyInfluence(-cash);
        if(propertyOwner != null){
            propertyOwner.moneyInfluence(cash);
        }
    }

    public void currPlayerGoBankrupt() {
        this.currPlayer.goBankrupt();
    }

    public void setCurrPlayerBalance(int balance) {
        currPlayer.setBalance(balance);
    }

    public void setCurrScenarioForPlayer(String currScenario) {
        this.currScenarioForPlayer = currScenario;
    }

    public String getCurrScenarioForPlayer(){
        return currScenarioForPlayer;
    }

    public void handleSquare(PropertySquare propertySquare){
        propertyCtrl.handleProperty(propertySquare, this);
    }

    public void handleSquare(ChanceSquare chanceSquare) {
        chanceCardCtrl.handleChanceCards(this);
    }

    public void giveOutOfJailCard() {
        this.outOfJailCards++;
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

    public boolean hasGetOutOfJailCard() {
        return currPlayer.getGetOutOfJailCards() > 0;
    }

    public int getTurnsInJail() {
        return currPlayer.getTurnsTakenInJail();
    }

    public void useGetOutOfJailCard() {
        currPlayer.useGetOutOfJailCard();
    }

    public void payIncomeTax() {
        gameBoardCtrl.payIncomeTax(this);
    }
}
