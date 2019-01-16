package model;

import controller.BankruptController;
import controller.ChanceCardController;
import controller.PropertyController;
import model.square.Square;
import model.square.property.PropertySquare;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;


class GameBoardTest {


    @Test
    void setSiblingsTest() {
        //Arrange
        MockGUI mockGUI = new MockGUI();
        BankruptController bankruptController = new BankruptController(mockGUI);
        ChanceCardController chanceCardController = new ChanceCardController(mockGUI);
        PropertyController propertyController = new PropertyController(mockGUI, bankruptController);

        GameBoard gameBoard = new GameBoard(propertyController, chanceCardController);

        Square[] squares = gameBoard.getSquareList();

        PropertySquare bernstoffsvej = (PropertySquare) squares[16];
        PropertySquare[] bernstoffsvejSiblingSquares = bernstoffsvej.getSiblingsSquares();
        PropertySquare hellerupvej = (PropertySquare) squares[18];
        PropertySquare strandvejen = (PropertySquare) squares[19];

        //Act
        boolean isFirstSiblingCorrect = false;
        boolean isSecondSiblingCorrect = false;
        for(PropertySquare brenstoffsvejSibling: bernstoffsvejSiblingSquares){
            if(brenstoffsvejSibling.equals(hellerupvej)){
                isFirstSiblingCorrect = true;
            } else if(brenstoffsvejSibling.equals(strandvejen)){
                isSecondSiblingCorrect= true;

            }
        }



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
        assertTrue(isFirstSiblingCorrect);
        assertTrue(isSecondSiblingCorrect);


    }



}