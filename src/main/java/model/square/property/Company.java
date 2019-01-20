package model.square.property;

public class Company extends PropertySquare {
    public Company(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares);
    }

    @Override
    public int getRentPrice() {
        int siblingsOwned = 0;
        for(PropertySquare sibling : super.getSiblingsSquares()) {
            if(this.getOwner().equals(sibling.getOwner())) {
                siblingsOwned = 1;
            }
        }
        return super.getRentPriceList()[siblingsOwned];
    }
}
