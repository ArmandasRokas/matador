package controller;

import java.awt.Color;

public class GameLogic {
    private int minPlayers, maxPlayers, startBalance;
    private Color[] colors;

    public GameLogic() {
        minPlayers = 3;
        maxPlayers = 6;
        startBalance = 1500;
        this.colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.MAGENTA}; //Virker kun for 6 spillere
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean controlPlayerCount(int numberOfPlayers) {
        boolean res = false;

        if(numberOfPlayers >= minPlayers && numberOfPlayers <= maxPlayers) {
            res = true;
        }
        return res;
    }

    public int getStartBalance() {
        return startBalance;
    }

    public Color[] getColors() {
        return colors;
    }
}
