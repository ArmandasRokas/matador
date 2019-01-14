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



        if(super.getIsOwned() && !playerController.getCurrPlayer().equals(super.getOwner())){ //pay rent.
            //TODO pay rent to owner. Do not forget overwrite setCurrScenarioForPlayer
        } else if (super.getIsOwned() && playerController.getCurrPlayer().equals(super.getOwner())){
            super.setCurrScenarioForPlayer(playerController.getCurrPlayerName() + " står på " + super.getSquareName() +
                    " som " + playerController.getCurrPlayerName() + " ejer selv.");

        } else if(!super.getIsOwned()){ //
            propertyController.buyProperty(this, playerController);
        }
    }

    @Override
    public int getRentPrice() {
        //TODO ckecks how many houses is build on the square

        return super.getRentPriceList()[numberOfHouses];
    }
}

