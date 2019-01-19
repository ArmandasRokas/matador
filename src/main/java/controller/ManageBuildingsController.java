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
    private GameRules gameRules;

    public ManageBuildingsController(GUIBoundary gui, GameRules gameRules, GameBoard gameBoard) {
        this.gui = gui;
        this.gameRules = gameRules;
        this.gameBoard = gameBoard;
        this.squareList = gameBoard.getSquareList();
    }

    public void buyHouse(PlayerController plCtrl, String squareName) {
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        StreetSquare streetSquare = (StreetSquare) squareList[squareIndex];

        if(streetSquare.getNumberOfHouses() == 5) {
            plCtrl.setCurrScenarioForPlayer("Der er allerede ét hotel på denne grund!");
        } else if(!gameRules.isBuyingBuildingsEvenly(streetSquare)) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " kan ikke udvide denne grund før de andre er på samme niveau");
        }
        else if(plCtrl.getCurrPlayerBalance() >= streetSquare.getHousePrice() && streetSquare.getNumberOfHouses() < 5) {
            plCtrl.currPlayerMoneyInfluence(-streetSquare.getHousePrice());
            streetSquare.buyAHouse();
            int numberOfHouses = streetSquare.getNumberOfHouses();
            gui.setHousing(squareIndex, numberOfHouses);
            gui.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
            gui.updateRentPrice(squareIndex, streetSquare.getRentPrice());
            plCtrl.setCurrScenarioForPlayer("Tillykke, " + plCtrl.getCurrPlayerName() + "! Du har udvidet " + squareName);
        } else if(plCtrl.getCurrPlayerBalance() < streetSquare.getHousePrice()) {
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har ikke penge nok.");
        } else {
            plCtrl.setCurrScenarioForPlayer("Der er sket en fejl, kontakt tekniker");
        }
    }

    public void sellHouse(PlayerController plCtrl, String squareName){
        int squareIndex = gameBoard.findSquareIndexByName(squareName);
        StreetSquare streetSquare = (StreetSquare) squareList[squareIndex];

        if(!gameRules.isSellingBuildingsEvenly(streetSquare)){
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " kan ikke sælge byggning på denne grund før de andre er på samme niveau.");
        } else {
            streetSquare.sellAHouse();
            int sellPrice = streetSquare.getHousePrice()/2;
            plCtrl.currPlayerMoneyInfluence(sellPrice);
            plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + ", du får " + sellPrice + " for at sælge din bygning på " + squareName);
            gui.setHousing(squareIndex, streetSquare.getNumberOfHouses());
            gui.updateRentPrice(streetSquare.getIndex(), streetSquare.getRentPrice());
        }
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
