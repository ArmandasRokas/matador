package controller;

import model.Cup;
import model.GameBoard;
import model.square.property.StreetSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import UI.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class ManageBuildingsControllerTest {
    MockGUI mockGUI ;
    GameRules gameRules;
    PlayerController playerController;
    BankruptController bankruptCtrl;
    PropertyController propertyCtrl;
    ChanceCardController cardController;
    GameBoardController gameBoardCtrl;
    GameBoard gameBoard ;
    Cup cup;

    @BeforeEach
    void setUp() {
        mockGUI = new MockGUI();
        gameRules = new GameRules();
        bankruptCtrl = new BankruptController(mockGUI);
        gameBoardCtrl = new GameBoardController(mockGUI);
        cardController = new ChanceCardController(mockGUI, gameBoardCtrl, bankruptCtrl);
        gameBoard = new GameBoard();
        cup = new Cup();
        propertyCtrl = new PropertyController(mockGUI, bankruptCtrl, cup);
        playerController = new PlayerController(mockGUI, gameRules, 3, propertyCtrl, cardController, gameBoardCtrl, bankruptCtrl);
        playerController.createPlayers();

    }

    @Test
    void getCurrPlayerSquarePossibleToBuildTest(){
        //Arrange
        StreetSquare rødovrevej = (StreetSquare) gameBoard.getSquareList()[1];
        StreetSquare hvidovrevej = (StreetSquare) gameBoard.getSquareList()[3];


        propertyCtrl.buyProperty(rødovrevej, playerController);
        propertyCtrl.buyProperty(hvidovrevej, playerController);

        ManageBuildingsController mbController = new ManageBuildingsController(mockGUI, gameRules, gameBoard);
        //Act
        int[] streetSquaresPossibleToBuildIndexes = mbController.getCurrPlayerSquarePossibleToBuildHousing(playerController);


        StreetSquare[] streetSquaresPossibleToBuild = new StreetSquare[2];

        streetSquaresPossibleToBuild[0] = (StreetSquare) gameBoard.getSquareList()[streetSquaresPossibleToBuildIndexes[0]];
        streetSquaresPossibleToBuild[1] = (StreetSquare) gameBoard.getSquareList()[streetSquaresPossibleToBuildIndexes[1]];

        boolean isFirstSquareOwned = false;
        boolean isSecondSquareOwned = false;


        for(StreetSquare street: streetSquaresPossibleToBuild){

            if(rødovrevej.equals(street)){
                isFirstSquareOwned = true;
            } else if(hvidovrevej.equals(street)){
                isSecondSquareOwned= true;
            }
        }

        //Assert
        assertTrue(isFirstSquareOwned);
        assertTrue(isSecondSquareOwned);

    }

}
