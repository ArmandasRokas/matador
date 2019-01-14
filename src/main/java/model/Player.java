package model;


import model.square.property.PropertySquare;

public class Player {
    private String name;
    private int playerID, balance, currentPosition, getOutOfPrisonCards;
    private PropertySquare[] properties;
    private boolean isBankrupt;

    public Player(int playerID, String name, int balance) {
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        this.isBankrupt = false;
        currentPosition = 0;
        getOutOfPrisonCards = 0;
        properties = new PropertySquare[28];
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

    public void moneyInfluence(int cash) {
        balance += cash;
    }

    public String toString() {
        return name;
    }

    public void addProperty(PropertySquare square){
        for(int i = 0; i<28; i++){
            if(properties[i] == null){
                properties[i] = square;
                break;
            }
        }
    }

    public PropertySquare[] getProperties(){
        return properties;
    }

    public void goBankrupt(){
        this.isBankrupt = true;
    }

    public boolean getBankrupt(){
        return this.isBankrupt;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
