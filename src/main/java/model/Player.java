package model;

public class Player {
    private String name;
    private int playerID, balance, currentPosition, getOutOfPrisonCards;
//    private Property[] properties; //TODO Afkommenter når at Property bliver oprettet

    public Player(int playerID, String name, int balance) {
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        currentPosition = 0;
        getOutOfPrisonCards = 0;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setPosition(int newPosition) {
        this.currentPosition = newPosition;
    }

    public int getPlayerID() {
        return playerID;
    }
}
