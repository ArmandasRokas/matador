package ui;

import gui_fields.GUI_Ownable;
import gui_fields.GUI_Player;

import java.awt.*;

public class MockGUI extends GUIBoundary {

    public MockGUI() {
        super();
    }

    @Override
    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = 3;
        super.playerList = new GUI_Player[playerCount];
        return playerCount;
    }

    @Override
    public String[] askForNames(int playerCount) {
        return new String[]{"a", "b", "c"};
    }

    @Override
    public void setupPlayer(int playerID, String name, int balance, Color color) { }

    @Override
    public void movePlayer(int previousPosition, int newPosition, int playerID) { }

    public boolean askToBuyProperty(int playerID, int fieldID){

        return true;
    }
    @Override
    public void setOwnerOnSquare(int playerID, int squareIndex, int rentPrice){

    }

    public void updateBalance(int playerID, int balance) {
    }

}
