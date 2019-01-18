package controller;

import model.GameBoard;
import model.square.property.StreetSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class ManageBuildingsControllerTest {

    MockGUI mockGUI ;
    GameLogic gameLogic ;
    PlayerController playerController;
    BankruptController bankruptController;
    PropertyController propertyController;
    ChanceCardController cardController;
    GameBoardController gameBoardCtrl;
    GameBoard gameBoard ;
    @BeforeEach
    void setUp() {
        mockGUI = new MockGUI();
        gameLogic = new GameLogic();
        bankruptController = new BankruptController(mockGUI);
        cardController = new ChanceCardController(mockGUI);
        gameBoard = new GameBoard();
        propertyController = new PropertyController(mockGUI, bankruptController);
        gameBoardCtrl = new GameBoardController(mockGUI);
        playerController = new PlayerController(mockGUI, gameLogic, 3, propertyController, cardController, gameBoardCtrl);
        playerController.createPlayers();

    }

    @Test
    void getCurrPlayerSquarePossibleToBuildTest(){
        //Arrange
        StreetSquare rødovrevej = (StreetSquare) gameBoard.getSquareList()[1];
        StreetSquare hvidovrevej = (StreetSquare) gameBoard.getSquareList()[3];


        propertyController.buyProperty(rødovrevej, playerController);
        propertyController.buyProperty(hvidovrevej, playerController);

        ManageBuildingsController mbController = new ManageBuildingsController(mockGUI, gameBoard);
        //Act
        int[] streetSquaresPossibleToBuildIndexes = mbController.getCurrPlayerSquarePossibleToBuild(playerController);


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
