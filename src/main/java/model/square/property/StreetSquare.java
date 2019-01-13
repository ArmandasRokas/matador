package model.square.property;

import controller.PlayerController;
import controller.PropertyController;

public class StreetSquare extends PropertySquare {

    private int numberOfHouses;



    public StreetSquare(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares, PropertyController propertyController){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares, propertyController);
        this.numberOfHouses = 0;
    }


    @Override
    public void landedOn(PlayerController playerController) {



        if(super.getIsOwned() && !playerController.getCurrPlayer().equals(owner)){
            //TODO pay rent to owner
        } else if (super.getIsOwned() && playerController.getCurrPlayer().equals(owner)){
            super.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " står på " + super.getSquareName() +
                    " som " + playerController.getCurrPlayerName() + " ejer selv.");

        } else if(!super.getIsOwned()){
            propertyController.buyProperty(this, playerController);
        }

    }

    @Override
    protected int getRentPrice() {
        return 0;
    }



    @Override
//    protected int getRentPrice() {
//
//        int toBePayed = rentPrice[0]; //TODO checks how many houses are on field and change rent price
//        //TODO Create test for this if-statement
//        if(isPropertySetOwned()) {
//            toBePayed = toBePayed * 2;
//        }
//
//
//        p.addToCash(-toBePayed);
//        owner.addToCash(toBePayed);
//
//
//
//        return toBePayed;
//    }


    public int getGroupID() {
        return groupID;
    }


//    public void setSiblingSquare(PropertySquare ps) {
//        siblingSquares.add(ps);
//    }



//    public boolean canBuild(){
//
//        for(PropertySquare propertySquare: siblingSquares){
//            if(!propertySquare.getOwner().equals(owner)){
//                return false;
//            }
//        }
//        return true;
//    }

//    public void buildHouse(){
//        numberOfHouses++;
//    }

    /**
     * Method is used for test
     *
     * @return
     */

//    public ArrayList<PropertySquare> getSiblingSquares(){
//
//        return siblingSquares;
//
//    }



}

