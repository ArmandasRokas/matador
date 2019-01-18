package controller;

import model.Player;

import java.awt.Color;

public class GameLogic {
    private int minPlayers, maxPlayers, startBalance;
    private Color[] colors;

    public GameLogic() {
        minPlayers = 2;
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

    public Player winnerFound(Player[] playerList) {
        int activePlayerCount = 0;
        Player latestPlayer = null;

        for(Player p : playerList) {
            if(!p.getBankrupt()) {
                activePlayerCount++;
                latestPlayer = p;
            }
        }
        if(activePlayerCount > 1) {
            latestPlayer = null;
        }
        return latestPlayer;
    }

    public boolean passStart(int currPosition, int newPosition) {
        return newPosition < currPosition;
    }
}
