package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

import java.awt.Color;

public class PlayerController {
    private Player[] playerList;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Player currPlayer;

    public PlayerController(GUIBoundary guiB, GameLogic gL, int numberOfPlayers) {
        this.guiB = guiB;
        this.gL = gL;
        playerList = new Player[numberOfPlayers];
    }

    public void createPlayers() {
        String[] names = guiB.askForNames(playerList.length);
        Color[] carColors = gL.getColors();

        for(int i = 0; i < playerList.length; i++){
            playerList[i] = new Player(i, names[i], gL.getStartBalance());
            guiB.setUpPlayer(playerList[i].getPlayerID(), playerList[i].getName(), playerList[i].getBalance(), carColors[i]);
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

    public PropertySquare[] getCurrPlayerPropertie(){
        return currPlayer.getProperties();
    }

    public void payPlayer(Player propertyOwner, int cash) {
        currPlayerMoneyInfluence(-cash);
        propertyOwner.moneyInfluence(cash);
    }
}
