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
        } else if(plCtrl.getCurrPlayerBalance() >= streetSquare.getHousePrice() && streetSquare.getNumberOfHouses() < 5) {
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

    public int[] getCurrPlayerSquarePossibleToBuildHousing(PlayerController playerCtrl){
        ArrayList<Integer> squaresPossibleToBuyHousing = new ArrayList<>();

        for(PropertySquare propertySquare: playerCtrl.getCurrPlayerProperties()){
            if(propertySquare instanceof StreetSquare && propertySquare.isSetOwned()){
                squaresPossibleToBuyHousing.add(propertySquare.getIndex());
            }
        }
        int[] indexesOfPossibleStreets = new int[squaresPossibleToBuyHousing.size()];

        for(int i=0; i < indexesOfPossibleStreets.length; i++ ){
            indexesOfPossibleStreets[i] = squaresPossibleToBuyHousing.get(i);
        }
        return indexesOfPossibleStreets;
    }

    public int[] getCurrPlayerSquarePossibleToSellHousing(PlayerController playerCtrl){
        ArrayList<Integer> squaresPossibleToSellHousing = new ArrayList<>();

        for(PropertySquare propertySquare: playerCtrl.getCurrPlayerProperties()){
            if(propertySquare instanceof StreetSquare && ((StreetSquare)propertySquare).getNumberOfHouses() > 0){
                squaresPossibleToSellHousing.add(propertySquare.getIndex());
            }
        }
        int[] convertToIntArray = new int[squaresPossibleToSellHousing.size()];

        for(int i=0; i < convertToIntArray.length; i++ ){
            convertToIntArray[i] = squaresPossibleToSellHousing.get(i);
        }
        return convertToIntArray;
    }
}