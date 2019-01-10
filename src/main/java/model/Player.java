package model;

import gui_fields.GUI_Player;

public class Player {
    private String name;
    private int balance, currentPosition, getOutOfPrisonCards;
    private GUI_Player piece; //TODO Ændre til playerNumber, da man ellers skal herned og ændrer hvis man skifter GUI
//    private Property[] properties;

    public Player(String name, int balance) {
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

    public void setPiece(GUI_Player piece) {
        this.piece = piece;
    }
}
