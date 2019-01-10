package controller;

import gui_fields.GUI_Car;
import gui_fields.GUI_Player;
import gui_main.GUI;
import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();

    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = gui.getUserInteger("Vælg antal spillere (3-6)", minPlayers, maxPlayers);
        return playerCount;
    }

    public GUI_Player setUpPlayer(String name, int balance, Color color) {
        GUI_Car car = new GUI_Car();
        car.setPrimaryColor(color);
        GUI_Player guiPlayer = new GUI_Player(name, balance, car);
        gui.addPlayer(guiPlayer);
        gui.getFields()[0].setCar(guiPlayer, true);
        return guiPlayer;
    }

    public String[] askForNames(int playerCount) { //TODO Fix at man ikke kan hedde det samme, da det overwriter den forrige GUI_Player
        String[] names = new String[playerCount];

        for(int i = 0 ; i < playerCount ; i++) {
            names[i] = gui.getUserString("Skriv navn for spiller nr. " + (i + 1));
        }

        return names;
    }
}
