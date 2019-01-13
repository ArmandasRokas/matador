package model.square.property;

import controller.PropertyController;
import model.Player;
import model.square.Square;

public abstract class PropertySquare extends Square {

    protected int price;                      //Price of the property
    protected int[] rentPrice;                  //Price of landing on the property
    protected final int groupID;
    protected Player owner;                   //Reference to the player that owns the property
    private boolean isOwned;                //Boolean to determine if the property is owned
    protected Square[] siblingSquares;   //Reference to the other property of same color
    protected int index;
    protected PropertyController propertyController;


    public PropertySquare(String scenario, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingsSqaures, PropertyController propertyController) {
        super(scenario, index);
        this.price = price;
        this.rentPrice = rentPrice;
        this.groupID = groupID;
        this.owner = null;
        isOwned = false;
        this.index = index;
        siblingSquares = new Square[numberOfSiblingsSqaures];
        this.propertyController = propertyController;
    }

    public boolean getIsOwned() {
        return isOwned;
    }


    public int getGroupID() {
        return groupID;
    }

    protected abstract int getRentPrice();

    public int getBuyPrice() {
        return price;
    }


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player p) {
        this.owner = p;
    }

    public void setIsOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }


    @Override
    public String getSquareName() {

        if (owner == null) {
            return super.squareName + " for " + price + "dkk";
        } else {
            return super.squareName;
        }

    }

}

