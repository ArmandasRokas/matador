package model.square.property;

import controller.PlayerController;
import controller.PropertyController;

public class StreetSquare extends PropertySquare {

    private int numberOfHouses;
    private int housePrice;


    public StreetSquare(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares, int housePrice){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares);
        this.numberOfHouses = 0;
        this.housePrice = housePrice;
    }

//    @Override
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

    public void sellAHouse(){
        numberOfHouses--;
    }
    public int getNumberOfHouses() {
        return  numberOfHouses;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public boolean isBuyingBuildingsEvenly() {
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
    public boolean isSellingBuildingsEvenly() {
        boolean res = true;
        for(PropertySquare siblingSquare : getSiblingsSquares()) {
            StreetSquare streetSquare = (StreetSquare)siblingSquare;
            if(getNumberOfHouses() < streetSquare.getNumberOfHouses()) {
                res = false;
                break;
            }
        }
        return res;
    }

}

