package model.square;

import controller.PlayerController;
import model.Player;

/**@author Hold 44
 * @version
 *
 * Defines the abstract Square class and all of its fields and methods
 * Class to represent the model.squares on board. Is not to be created instances of, since all model.squares are to be unique classes.
 */
public abstract class Square {
    protected String squareName;  //Name of square, used to describe the scenario of the turn
    private int index;
    private Player owner;                   //Reference to the player that owns the property, or null if property is owned by bank



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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player p) {
        this.owner = p;
    }
}
