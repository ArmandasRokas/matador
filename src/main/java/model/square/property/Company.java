package model.square.property;

import controller.PlayerController;
import controller.PropertyController;
import model.square.property.PropertySquare;

public class Company extends PropertySquare {
    public Company(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares);
    }

    @Override
    public int getRentPrice() {


        return super.getRentPriceList()[0];
        //TODO Hvis man ejer flere firmafelter stiger lejeprisen
    }
}
