package model.square.property;

import controller.PlayerController;
import controller.PropertyController;
import model.square.property.PropertySquare;

public class Transport extends PropertySquare {
    public Transport(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares);
    }

    @Override
    public int getRentPrice() {
        int rentPrice = super.getRentPriceList()[0];
        for(PropertySquare sibling : super.getSiblingsSquares()) {
            if(this.getOwner().equals(sibling.getOwner())) {
                rentPrice = rentPrice * 2;
            }
        }
        return rentPrice;
    }
}
