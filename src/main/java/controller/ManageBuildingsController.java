package controller;

import model.GameBoard;
import model.square.Square;
import model.square.property.PropertySquare;
import model.square.property.StreetSquare;
import ui.GUIBoundary;

import java.util.ArrayList;

public class ManageBuildingsController {
    private Square[] squareList;
    private GameBoard gameBoard;
    private GUIBoundary gui;

    public ManageBuildingsController(GUIBoundary gui, GameBoard gameBoard) {
        this.squareList = gameBoard.getSquareList();
        this.gameBoard = gameBoard;
        this.gui = gui;
    }

    public boolean buyHouse(PlayerController plCtrl, String squareName) {
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        Square square = squareList[squareIndex];
        StreetSquare street;
        if(square instanceof StreetSquare){
            street = (StreetSquare) square;
        } else {
            plCtrl.setCurrScenarioForPlayer("Der er sket en fejl, kontakt tekniker");
            return false;
        }

        if(street.getNumberOfHouses() == 5) {
            plCtrl.setCurrScenarioForPlayer("Der er allerede ét hotel på denne grund!");
        } else if(!street.isBuildingEvenly()) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " kan ikke udvide denne grund før de andre er på samme niveau");
        }
        else if(plCtrl.getCurrPlayerBalance() >= street.getHousePrice() && street.getNumberOfHouses() < 5) {
            plCtrl.currPlayerMoneyInfluence(-street.getHousePrice());
            street.buyAHouse();
            int numberOfHouses = street.getNumberOfHouses();
            gui.setHousing(squareIndex, numberOfHouses);
            gui.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
            gui.updateRentPrice(squareIndex, street.getRentPrice());
            plCtrl.setCurrScenarioForPlayer("Tillykke, " + plCtrl.getCurrPlayerName() + "! Du har udvidet " + squareName);
        } else if(plCtrl.getCurrPlayerBalance() < street.getHousePrice()) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har ikke penge nok.");
        } else {
            plCtrl.setCurrScenarioForPlayer("Der er sket en fejl, kontakt tekniker");
        }

        return true;
    }

    public boolean sellHouse(PlayerController plCtrl, String squareName){
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        Square square = squareList[squareIndex];
        StreetSquare street;
        if(square instanceof StreetSquare){
            street = (StreetSquare) square;
        } else {
            plCtrl.setCurrScenarioForPlayer("Der er sket en fejl, kontakt tekniker");
            return false;
        }

        street.sellAHouse();

        int sellPrice = street.getHousePrice()/2;
        plCtrl.currPlayerMoneyInfluence(sellPrice);
        plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + ", du får " + sellPrice + " for at sælge din bygning på " + squareName);
        gui.setHousing(squareIndex, street.getNumberOfHouses());
        return true;
    }


    public int[] getCurrPlayerSquarePossibleToBuild(PlayerController playerCtrl){
        int[] squaresPossibleToBuild = new int[28];

        for(PropertySquare property: playerCtrl.getCurrPlayerProperties()){
            if(property instanceof StreetSquare) {
                StreetSquare street = (StreetSquare) property;
                if(street.isSetOwned()){
                    for(int i = 0; i < squaresPossibleToBuild.length; i++ ){
                        if(squaresPossibleToBuild[i] == 0){
                            squaresPossibleToBuild[i] = street.getIndex();
                            break;
                        }
                    }
                }
            }
        }
        return squaresPossibleToBuild;
    }

    public int[] getCurrPlayerSquarePossibleToSellHousing(PlayerController playerCtrl){
        ArrayList<Integer> squaresPossibleToSellHousing = new ArrayList<>();

        for(PropertySquare propertySquare: playerCtrl.getCurrPlayerProperties()){
            if(propertySquare instanceof StreetSquare){
                StreetSquare street = (StreetSquare) propertySquare;
                if(street.getNumberOfHouses() > 0 ){
                    squaresPossibleToSellHousing.add(street.getIndex());
                }
            }
        }

        int[] convertToIntArray = new int[squaresPossibleToSellHousing.size()];
        for(int i=0; i < convertToIntArray.length; i++ ){
            convertToIntArray[i] = squaresPossibleToSellHousing.get(i);
        }
        return convertToIntArray;
    }
}
