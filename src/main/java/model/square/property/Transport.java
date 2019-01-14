package model.square.property;

import controller.PlayerController;
import controller.PropertyController;
import model.square.property.PropertySquare;

public class Transport extends PropertySquare {
    public Transport(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares, PropertyController propertyController){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares, propertyController);
    }

    @Override
    public int getRentPrice() {
        return 0;
    }

    @Override
    public void landedOn(PlayerController p) {

    }
}
