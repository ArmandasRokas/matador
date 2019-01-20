package model;

import model.square.property.PropertySquare;

import java.util.ArrayList;

public class Player {
    private String name;
    private int playerID, balance, currentPosition, getOutOfJailCards;
    private ArrayList<PropertySquare> properties;
    private boolean isBankrupt;
    private boolean isCurrPlayerInJail;
    private int turnsTakenInJail;

    public Player(int playerID, String name, int balance) {
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        this.isBankrupt = false;
        this.isCurrPlayerInJail = false;
        this.turnsTakenInJail = 0;
        currentPosition = 0;
        getOutOfJailCards = 0;
        properties = new ArrayList<>();
    }

    public String toString() {
        return name;
    }

    public void moneyInfluence(int cash) {
        balance += cash;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getBalance() {
        return balance;
    }

    public void setPosition(int newPosition) {
        this.currentPosition = newPosition;
    }
    public int getCurrentPosition() {
        return currentPosition;
    }

    public void addProperty(PropertySquare propertySquare){
        properties.add(propertySquare);
    }
    public ArrayList<PropertySquare> getProperties(){
        return properties;
    }

    public void setBankrupt(){
        this.isBankrupt = true;
    }
    public boolean isBankrupt(){
        return this.isBankrupt;
    }

    public void setIsCurrPlayerInJail(boolean isInJail){ this.isCurrPlayerInJail = isInJail;}
    public boolean getIsCurrPlayerInJail(){return this.isCurrPlayerInJail;}

    public void increaseTurnsTakenInJail() { turnsTakenInJail++; }
    public void resetTurnsTakenInJail() {
        turnsTakenInJail = 0;
    }
    public int getTurnsTakenInJail() {
        return turnsTakenInJail;
    }

    public void addOutOfJailCard() {
        this.getOutOfJailCards++;
    }
    public void useGetOutOfJailCard() {
        this.getOutOfJailCards = getOutOfJailCards - 1;
    }
    public int getGetOutOfJailCards() { return getOutOfJailCards; }

    public String getName() { return name; }

    public int getPlayerID() {
        return playerID;
    }
}