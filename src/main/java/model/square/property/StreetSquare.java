package model.square.property;

import controller.PlayerController;
import controller.PropertyController;

public class StreetSquare extends PropertySquare {

    private int numberOfHouses;
    private int housePrice;


    public StreetSquare(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares, PropertyController propertyController, int housePrice){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares, propertyController);
        this.numberOfHouses = 0;
        this.housePrice = housePrice;
    }


//    @Override
//    public void landedOn(PlayerController playerController) {
//
//        if(super.getOwner() != null && !playerController.getCurrPlayer().equals(super.getOwner())){ //pay rent.
//            propertyController.payRent(this, playerController);
//        } else if (super.getOwner() != null && playerController.getCurrPlayer().equals(super.getOwner())){ //owned by current player
//            playerController.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " står på " + this +
//                    " som " + playerController.getCurrPlayerName() + " ejer selv.");
//
//        } else if(super.getOwner() == null){ //buy property
//            propertyController.buyProperty(this, playerController);
//        }
//    }

    @Override
    public int getRentPrice() {
        //TODO ckecks how many houses is build on the square

        int rentPrice = getRentPriceList()[numberOfHouses];
        if(isSetOwned() && numberOfHouses < 1){
            rentPrice = rentPrice*2;
        }
        return rentPrice;
    }

    public void buyAHouse() {
        numberOfHouses++;
    }

    public int getNumberOfHouses() {
        return  numberOfHouses;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public boolean isBuildingEvenly() {
        boolean res = true;
        for(PropertySquare siblingSquare : getSiblingsSquares()) {
            StreetSquare streetSquare = (StreetSquare)siblingSquare;
            if(getNumberOfHouses() > streetSquare.getNumberOfHouses()) {
                res = false;
                break;
            }
        }
        return res;
    }
}

