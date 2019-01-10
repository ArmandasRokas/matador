package controller;

public class GameLogic {
    private int minPlayers, maxPlayers, startBalance;

    public GameLogic() {
        minPlayers = 3;
        maxPlayers = 6;
        startBalance = 1500;
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
}
