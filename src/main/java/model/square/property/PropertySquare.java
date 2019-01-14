package model.square.property;

import controller.PropertyController;
import model.Player;
import model.square.Square;

public abstract class PropertySquare extends Square {

    private int price;                      //Price of the property
    private int[] rentPriceList;                  //Price of landing on the property
    private final int groupID;
    private Player owner;                   //Reference to the player that owns the property
    private boolean isOwned;                //Boolean to determine if the property is owned
    private Square[] siblingSquares;   //Reference to the other property of same color
    private final int index;
    protected PropertyController propertyController;


    public PropertySquare(String scenario, int[] rentPriceList, int price, int groupID, int index, int numberOfSiblingsSqaures, PropertyController propertyController) {
        super(scenario, index);
        this.price = price;
        this.rentPriceList= rentPriceList;
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

    public abstract int getRentPrice();

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

    public int[] getRentPriceList(){
        return rentPriceList;
    }

}

