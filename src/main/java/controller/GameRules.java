package controller;

import model.Player;
import model.square.property.PropertySquare;
import model.square.property.StreetSquare;

import java.awt.Color;

public class GameRules {
    private int minPlayers, maxPlayers, startBalance;
    private Color[] colors;
    private int luxuryTax;
    private int incomeTax;

    private int getOutOfJailBail;

    public GameRules() {
        this.minPlayers = 3;
        this.maxPlayers = 6;
        this.startBalance = 1500;
        this.colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW, Color.MAGENTA};
        this.luxuryTax = 100;
        this.incomeTax = 200;
        this.getOutOfJailBail = 50;
    }

    public boolean controlPlayerCount(int numberOfPlayers) {
        boolean res = false;

        if(numberOfPlayers >= minPlayers && numberOfPlayers <= maxPlayers) {
            res = true;
        }
        return res;
    }

    public Player winnerFound(Player[] playerList) {
        int activePlayerCount = 0;
        Player latestPlayer = null;

        for(Player p : playerList) {
            if(!p.isBankrupt()) {
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

    //Getters and Setters
    public int getMinPlayers() {
        return minPlayers;
    }
    public int getMaxPlayers() {
        return maxPlayers;
    }
    public int getStartBalance() {
        return startBalance;
    }

    public Color[] getColors() {
        return colors;
    }

    public int getLuxuryTax() {
        return this.luxuryTax;
    }

    public int getIncomeTax() {
        return incomeTax;
    }

    public int getGetOutOfJailBail() {
        return getOutOfJailBail;
    }

    public boolean isBuyingBuildingsEvenly(PropertySquare propertySquare) {
        boolean res = true;
        StreetSquare streetSquare = (StreetSquare)propertySquare;

        for(PropertySquare siblingSquare : propertySquare.getSiblingsSquares()) {
            StreetSquare streetStreetSquare = (StreetSquare)siblingSquare;

            if(streetSquare.getNumberOfHouses() > streetStreetSquare.getNumberOfHouses()) {
                res = false;
                break;
            }
        }
        return res;
    }

    public boolean isSellingBuildingsEvenly(PropertySquare propertySquare) {
        boolean res = true;
        StreetSquare streetSquare = (StreetSquare)propertySquare;

        for(PropertySquare siblingSquare : propertySquare.getSiblingsSquares()) {
            StreetSquare streetStreetSquare = (StreetSquare)siblingSquare;

            if(streetSquare.getNumberOfHouses() < streetStreetSquare.getNumberOfHouses()) {
                res = false;
                break;
            }
        }
        return res;
    }
}
