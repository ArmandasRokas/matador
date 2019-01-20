package model.square.property;

public class StreetSquare extends PropertySquare {
    private int numberOfHouses;
    private int housePrice;

    public StreetSquare(String squareName, int[] rentPrice, int price, int groupID, int index, int numberOfSiblingSquares, int housePrice){
        super(squareName, rentPrice, price,groupID, index, numberOfSiblingSquares);
        this.numberOfHouses = 0;
        this.housePrice = housePrice;
    }

    @Override
    public int getRentPrice() {
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
}