package model;


import model.square.Square;
import model.square.property.PropertySquare;

public class Player {
    private String name;
    private int playerID, balance, currentPosition, getOutOfPrisonCards;
    private PropertySquare[] properties;

    public Player(int playerID, String name, int balance) {
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
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

    public void addProperty(PropertySquare square){
        for(int i = 0; i<28; i++){
            if(properties[i] == null){
                properties[i] = square;
            }
        }
    }

    public PropertySquare[] getProperties(){
        return properties;
    }
}
