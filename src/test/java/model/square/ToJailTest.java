package model.square;

import controller.*;
import model.GameBoard;
import model.square.property.PropertySquare;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class ToJailTest {

    @Test
    void testToJail(){
        //Arrange
        MockGUI mockGUI = new MockGUI();
        GameLogic gL = new GameLogic();
        PlayerController playerController = new PlayerController(mockGUI, gL, 3);
        ChanceCardController cdCtrl = new ChanceCardController(mockGUI);
        BankruptController bCtrl = new BankruptController(mockGUI);
        PropertyController propCtrl = new PropertyController(mockGUI, bCtrl);
        GameBoard gameBoard = new GameBoard(propCtrl, cdCtrl);

        //Act

        playerController.movePlayerToSquare(10);


        assertEquals(10,playerController.getCurrPlayerPos());

    }


}