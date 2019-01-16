package model.square.property;

import controller.*;
import model.GameBoard;
import model.square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class PropertySquareTest {

    private ChanceCardController chanceCardController;
    private PropertyController propertyController;
    private BankruptController bankruptController;
    private MockGUI mockGUI;
    private GameBoard gameBoard;
    private Square[] squares;
    private PlayerController playerController;
    private GameLogic gameLogic;

    @BeforeEach
    void setup() {
        //Arrange
        mockGUI = new MockGUI();
        bankruptController = new BankruptController(mockGUI);
        chanceCardController = new ChanceCardController(mockGUI);
        propertyController = new PropertyController(mockGUI, bankruptController);

        gameBoard = new GameBoard(propertyController, chanceCardController);

        squares = gameBoard.getSquareList();
        GameLogic gameLogic = new GameLogic();
        playerController = new PlayerController(mockGUI, gameLogic, 3);
        playerController.createPlayers();
    }
    @Test
    void isSetOwnedTest(){

        //Arrange
        PropertySquare roedovrevej = (PropertySquare) squares[1];
        PropertySquare hvidovrevej = (PropertySquare) squares[3];

        propertyController.buyProperty(roedovrevej, playerController);
        propertyController.buyProperty(hvidovrevej, playerController);

        //Act
        boolean isSetOwned1 = roedovrevej.isSetOwned();
        boolean isSetOwned2 = hvidovrevej.isSetOwned();

        //Assert
        assertTrue(isSetOwned1);
        assertTrue(isSetOwned2);

    }



}