package controller;

import gui_fields.GUI_Player;
import gui_main.GUI;

public class GUIBoundary {
    private GUI gui = new GUI();

    public int getUserInteger(String s, int minPlayers, int maxPlayers) {
        int res = gui.getUserInteger(s, minPlayers, maxPlayers);
        return res;
    }

    public GUI_Player setUpPlayer(String name, int balance) {
        GUI_Player guiPlayer = new GUI_Player(name, balance);
        gui.addPlayer(guiPlayer);
        gui.getFields()[0].setCar(guiPlayer, true);
        return guiPlayer;
    }

    public String[] askForNames(int playerCount) {
        String names[] = new String[playerCount];
        for(int i = 0 ; i < playerCount ; i++) {
            names[i] = gui.getUserString("Skriv navn for spiller nr. " + (i + 1));
        }

        return names;
    }
}
