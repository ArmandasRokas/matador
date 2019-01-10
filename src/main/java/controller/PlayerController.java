package controller;

import model.Player;

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
    }

    public void changePlayer() {
        int currID = currPlayer.getPlayerID();
        currID = (currID + 1) % playerList.length;
        currPlayer = playerList[currID];
    }
}
