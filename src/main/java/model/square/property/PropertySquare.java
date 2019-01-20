package model.square.property;

import controller.PlayerController;
import controller.PropertyController;
import model.Player;
import model.square.Square;

public abstract class PropertySquare extends Square {
    private int price;
    private int[] rentPriceList;
    private final int groupID;
    private PropertySquare[] siblingSquares;

    public PropertySquare(String scenario, int[] rentPriceList, int price, int groupID, int index, int numberOfSiblingsSqaures) {
        super(scenario, index);
        this.price = price;
        this.rentPriceList= rentPriceList;
        this.groupID = groupID;
        siblingSquares = new PropertySquare[numberOfSiblingsSqaures];
    }

    @Override
    public void landedOn(PlayerController playerController) {
        playerController.handleSquare(this);
    }

    public int getGroupID() {
        return groupID;
    }

    public abstract int getRentPrice();

    public int getBuyPrice() {
        return price;
    }

    @Override
    public String toString() {
        if (super.getOwner() == null) {
            return super.toString() + " for " + price + "dkk";
        } else {
            return super.toString();
        }
    }

    public int[] getRentPriceList(){
        return rentPriceList;
    }

    public void setSiblingSquare(PropertySquare propertySquare) {
        for (int i = 0; i < siblingSquares.length; i++){
            if(siblingSquares[i] == null){
                siblingSquares[i] = propertySquare;
                break;
            }
        }
    }

    public boolean isSetOwned(){
        boolean res = false;
        int siblingsOwned = 0;

        for(PropertySquare propertySquare: siblingSquares){
            if(this.getOwner() != null && this.getOwner().equals(propertySquare.getOwner())){
                    siblingsOwned++;
            }
        }

        if(siblingsOwned == siblingSquares.length){
            res = true;
        }
        return res;
    }

    public PropertySquare[] getSiblingsSquares(){
        return siblingSquares;
    }
}