package controller;

import model.Player;
import model.square.property.PropertySquare;
import model.square.property.StreetSquare;
import ui.GUIBoundary;

import java.awt.Color;

public class PlayerController {
    private Player[] playerList;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Player currPlayer;
    private String currScenarioForPlayer;
    private PropertyController propertyCtrl;
    private int turnsTakenInJail;

    public PlayerController(GUIBoundary guiB, GameLogic gL, int numberOfPlayers, PropertyController propertyCtrl) {
        this.propertyCtrl = propertyCtrl;
        this.guiB = guiB;
        this.gL = gL;
        this.turnsTakenInJail = 0;

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

    public void movePlayer(int rollScore) {
        int currPosition = currPlayer.getCurrentPosition();
        int newPosition = (rollScore + currPosition) % 40;
        currPlayer.setPosition(newPosition);                                    //TODO Fix start indkomst
        guiB.movePlayer(currPosition, newPosition,currPlayer.getPlayerID());

        if(newPosition < currPosition) {
            currPlayerMoneyInfluence(200);
            guiB.updateBalance(currPlayer.getPlayerID(), currPlayer.getBalance());
        }
    }
    public void movePlayerToSquare(int index){
        int currPosition = currPlayer.getCurrentPosition();
        currPlayer.setPosition(index);
        guiB.movePlayer(currPosition, index, getCurrPlayerID());

    }

    public int[] getCurrPlayerSquarePossibleToBuild(){
        int[] squaresPossibleToBuild = new int[28];

        for(PropertySquare property: currPlayer.getProperties()){
            if(property instanceof StreetSquare) {
                StreetSquare street = (StreetSquare) property;
                if(street.isSetOwned()){
                    for(int i = 0; i < squaresPossibleToBuild.length; i++ ){
                        if(squaresPossibleToBuild[i] == 0){
                            squaresPossibleToBuild[i] = street.getIndex();
                            break;
                        }
                    }
                }
            }
        }
        return squaresPossibleToBuild;
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

    public boolean getIsCurrPlayerInJail() {
        return currPlayer.getIsCurrPlayerInJail();
    }

    public void setCurrPlayerIsInJail(boolean isInJail) {
        if(!isInJail) {
            currPlayer.resetTurnsTakenInJail();
        }
        currPlayer.setIsCurrPlayerInJail(isInJail);
    }

    public void handleSquare(PropertySquare propertySquare){
        propertyCtrl.handleProperty(propertySquare, this);
    }
}
