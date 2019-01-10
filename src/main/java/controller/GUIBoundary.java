package controller;

import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;
import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();
    private GUI_Player[] playerList;
    private GUI_Field[] fieldList = gui.getFields();

    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = gui.getUserInteger("Vælg antal spillere (3-6)", minPlayers, maxPlayers);
        playerList = new GUI_Player[playerCount];
        return playerCount;
    }

    public GUI_Player setUpPlayer(int playerID, String name, int balance, Color color) {
        GUI_Car car = new GUI_Car();
        car.setPrimaryColor(color);
        GUI_Player guiPlayer = new GUI_Player(name, balance, car);
        playerList[playerID] = guiPlayer;
        gui.addPlayer(playerList[playerID]);
        fieldList[0].setCar(playerList[playerID], true);
        return guiPlayer;
    }

    public String[] askForNames(int playerCount) { //TODO Fix at man ikke kan hedde det samme, da det overwriter den forrige GUI_Player
        String[] names = new String[playerCount];

        for(int i = 0 ; i < playerCount ; i++) {
            names[i] = gui.getUserString("Skriv navn for spiller nr. " + (i + 1));
        }

        return names;
    }

    public void movePlayer(int previousPosition, int newPosition, int playerID) {
        fieldList[previousPosition].setCar(playerList[playerID],false);
        fieldList[newPosition].setCar(playerList[playerID],true);
    }
}
