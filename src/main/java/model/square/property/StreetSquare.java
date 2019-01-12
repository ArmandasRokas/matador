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


//        if(isOwned && !p.equals(owner)){
//            int rentPrice = getRentPrice();
//
//            payRent(p);
//
//
//
//            super.currScenarioForPlayer = p.getName() + " står på " + super.getName() + ", som ejes af " + owner.getName() +
//                    ". Du har betalt " + rentPrice + "DKK til " + owner.getName();
//
//
//        } else if (isOwned && p.equals(owner)){
//            super.playerAction = p.getName() + " står på " + super.toString() +
//                    " som " + p.getName() + " ejer selv.";
//        } else
        if(!isOwned){
//                res = true;
            propertyController.buyProperty(this, playerController);
        }

    }

    @Override
    protected int getRentPrice() {
        return 0;
    }

    //    public void buildHouse(){
//        numberOfHouses++;
//    }
//

    //    public boolean canBuild(){
//
//        for(PropertySquare propertySquare: siblingSquares){
//            if(!propertySquare.getOwner().equals(owner)){
//                return false;
//            }
//        }
//        return true;
//    }





//    public boolean isPropertySetOwned() {
//        boolean res = false;
//
//        //     if(owner.equals(siblingSquare.getOwner())) {
//        //         res = true;
//        //     }
//        return res;
//    }


//    private void payRent(Player p){
//
//    }

    @Override
//    protected int getRentPrice() {
//
//        int toBePayed = rentPrice[0]; //TODO checks how many houses are on field and change rent price
//        //TODO Create test for this if-statement
////        if(isPropertySetOwned()) {
////            toBePayed = toBePayed * 2;
////        }
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

