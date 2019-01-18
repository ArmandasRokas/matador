package controller;

import model.GameBoard;
import model.Player;
import model.square.property.StreetSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    MockGUI mockGUI ;
    GameLogic gameLogic ;
    PlayerController playerController;
    BankruptController bankruptController;
    PropertyController propertyController;
    GameBoardController gameBoardCtrl;
    ChanceCardController cardController;
    GameBoard gameBoard ;

    @BeforeEach
    void setUp(){
        mockGUI = new MockGUI();
        gameLogic = new GameLogic();
        bankruptController = new BankruptController(mockGUI);
        gameBoardCtrl = new GameBoardController(mockGUI);
        cardController = new ChanceCardController(mockGUI, gameBoardCtrl);
        gameBoard = new GameBoard();
        propertyController = new PropertyController(mockGUI,bankruptController);
        playerController = new PlayerController(mockGUI, gameLogic, 3, propertyController, cardController);
        playerController.createPlayers();



    }

    @Test
    void movePlayer() {
        //TODO add test if player can go in a circle correctly. Check for off-by-one bug
        Player[] players = playerController.getPlayerList();
        playerController.movePlayer(1, true);

        assertEquals(1, players[0].getCurrentPosition());
    }


    @Test
    void getCurrPlayerSquarePossibleToBuildTest(){
        //Arrange
        StreetSquare rødovrevej = (StreetSquare) gameBoard.getSquareList()[1];
        StreetSquare hvidovrevej = (StreetSquare) gameBoard.getSquareList()[3];


        propertyController.buyProperty(rødovrevej, playerController);
        propertyController.buyProperty(hvidovrevej, playerController);


        //Act
        int[] streetSquaresPossibleToBuildIndexes = playerController.getCurrPlayerSquarePossibleToBuild();


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