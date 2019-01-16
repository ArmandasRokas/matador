package model.square.property;

import controller.PlayerController;
import controller.PropertyController;
import model.Player;
import model.square.Square;

public abstract class PropertySquare extends Square {

    private int price;                      //Price of the property
    private int[] rentPriceList;                  //Price of landing on the property
    private final int groupID;
    private PropertySquare[] siblingSquares;   //Reference to the other property of same color
    private final int index;
    protected PropertyController propertyController;


    public PropertySquare(String scenario, int[] rentPriceList, int price, int groupID, int index, int numberOfSiblingsSqaures, PropertyController propertyController) {
        super(scenario, index);
        this.price = price;
        this.rentPriceList= rentPriceList;
        this.groupID = groupID;
        this.index = index;
        siblingSquares = new PropertySquare[numberOfSiblingsSqaures];
        this.propertyController = propertyController;

    }

    @Override
    public void landedOn(PlayerController playerController) {

        if(super.getOwner() != null && !playerController.getCurrPlayer().equals(super.getOwner())){ //pay rent.
            propertyController.payRent(this, playerController);
        } else if (super.getOwner() != null && playerController.getCurrPlayer().equals(super.getOwner())){ //owned by current player
            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " står på " + this +
                    " som " + playerController.getCurrPlayerName() + " ejer selv.");

        } else if(super.getOwner() == null){ //buy property
            propertyController.buyProperty(this, playerController);
        }
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
            if(this.getOwner().equals(propertySquare.getOwner())){
                    siblingsOwned++;
            }
        }
        if(siblingsOwned == siblingSquares.length){
            res = true;
        }

        return res;
    }

    /**
     * The method is used for testing purposes
     */
    public PropertySquare[] getSiblingsSquares(){
        return siblingSquares;
    }
}

