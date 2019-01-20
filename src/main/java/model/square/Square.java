package model.square;

import controller.PlayerController;
import model.Player;

public abstract class Square {
    private String squareName;
    private int index;
    private Player owner;

    public Square(String squareName, int index){
        this.squareName = squareName;
        this.index = index;
        this.owner = null;
    }

    public abstract void landedOn(PlayerController p);

    @Override
    public String toString(){
        return squareName;
    }

    public String getSquareName(){
        return squareName;
    }

    public int getIndex(){
        return index;
    }

    public void setOwner(Player p) {
        this.owner = p;
    }
    public Player getOwner() {
        return owner;
    }
}