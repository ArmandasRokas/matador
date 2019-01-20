package model;

import controller.BankruptController;
import controller.ChanceCardController;
import controller.GameBoardController;
import controller.PropertyController;
import model.square.Square;
import model.square.property.PropertySquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import UI.MockGUI;

import static org.junit.jupiter.api.Assertions.*;


class GameBoardTest {
    private ChanceCardController chanceCardController;
    private PropertyController propertyController;
    private BankruptController bankruptController;
    private GameBoardController gameBoardCtrl;
    private MockGUI mockGUI;
    private GameBoard gameBoard;
    private Square[] squares;
    private Cup cup;

    @BeforeEach
    void setup() {
        //Arrange
        mockGUI = new MockGUI();
        bankruptController = new BankruptController(mockGUI);
        gameBoardCtrl = new GameBoardController(mockGUI);
        chanceCardController = new ChanceCardController(mockGUI, gameBoardCtrl, bankruptController);
        cup = new Cup();
        propertyController = new PropertyController(mockGUI, bankruptController, cup);

        gameBoard = new GameBoard();

        squares = gameBoard.getSquareList();
    }

    @Test
    void setNumberOfSiblingsTest() {
        //Act
        boolean isNumberOfSiblingsCorrect = true;
        for(Square square: squares){

            if(square instanceof PropertySquare){
                PropertySquare propertySquare = (PropertySquare) square;
                PropertySquare[] siblingsSquares = propertySquare.getSiblingsSquares();

                for(PropertySquare siblingSquare: siblingsSquares){
                    if(siblingSquare == null){
                        isNumberOfSiblingsCorrect = false;
                    }
                }
            }
        }

        //Assert
        assertTrue(isNumberOfSiblingsCorrect); // all property squares has a correct number of sibling squares.
    }

    @Test
    void setCorrectSiblings() {
        //Arrange
        PropertySquare bernstoffsvej = (PropertySquare) squares[16];
        PropertySquare[] bernstoffsvejSiblingSquares = bernstoffsvej.getSiblingsSquares();
        PropertySquare hellerupvej = (PropertySquare) squares[18];
        PropertySquare strandvejen = (PropertySquare) squares[19];

        //Act
        boolean isFirstSiblingCorrect = false;
        boolean isSecondSiblingCorrect = false;

        for(PropertySquare bernstoffsvejSibling: bernstoffsvejSiblingSquares){

            if(bernstoffsvejSibling.equals(hellerupvej)){
                isFirstSiblingCorrect = true;
            } else if(bernstoffsvejSibling.equals(strandvejen)){
                isSecondSiblingCorrect= true;

            }
        }

        //Assert
        assertTrue(isFirstSiblingCorrect);
        assertTrue(isSecondSiblingCorrect);
    }
}