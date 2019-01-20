package UI;

import gui_fields.GUI_Player;
import ui.GUIBoundary;

import java.awt.*;

//Class to test program around the issue of user communication
public class MockGUI extends GUIBoundary {
    private int amountOfPlayers;
    private String[] names;

    public MockGUI() {
        super();

        amountOfPlayers = 3;
        names = new String[]{"Player 1", "Player 2", "Player 3", "Player 4", "Player 5", "Player 6"};
    }

    @Override
    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = amountOfPlayers;
        super.setPlayerList(new GUI_Player[playerCount]);
        return playerCount;
    }

    @Override
    public String[] askForNames(int playerCount) {
        return names;
    }

    @Override
    public boolean askToBuyProperty(int playerID, String name){
        return true;
    }

    //Empty calls to avoid GUI interaction
    @Override
    public void setOwnerOnSquare(int playerID, int squareIndex, int rentPrice){
    }
    @Override
    public void setupPlayer(int playerID, String name, int balance, Color color) { }
    @Override
    public void movePlayer(int previousPosition, int newPosition, int playerID) { }
    @Override
    public void updateBalance(int playerID, int balance) {
    }
}