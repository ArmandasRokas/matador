//package controller;
//
//import model.GameBoard;
//import model.Player;
//import model.square.Square;
//import model.square.property.PropertySquare;
//import org.junit.jupiter.api.Test;
//import UI.MockGUI;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PropertyControllerTest {
//
//    @Test
//    void buyPropertyTest() {
//
//        //Arrange
//        MockGUI gui = new MockGUI();
//        BankruptController bankruptCtrl = new BankruptController(gui);
//        PropertyController propertyCtrl = new PropertyController(gui, bankruptCtrl);
//
//        PlayerController playerController = new PlayerController(gui, new GameRules(), 1);
//        playerController.createPlayers();
//        Player player = playerController.getCurrPlayer();
//        int playerBalanceBeforeBuying = playerController.getCurrPlayerBalance();
//
//        GameBoard gameBoard = new GameBoard(propertyCtrl, new ChanceCardController(gui));
//        PropertySquare square = (PropertySquare) gameBoard.getSquareList()[1];
//
//        //Act
//
//        propertyCtrl.buyProperty(square, playerController);
//        int playerBalanceAfterBuying = playerController.getCurrPlayerBalance();
//
//        //Assert
//        assertEquals(player, square.getOwner());
//        assertTrue(square.getIsOwned());
//        assertTrue(playerBalanceAfterBuying < playerBalanceBeforeBuying);
//        assertEquals(square, playerController.getCurrPlayerProperties()[0]);
//
//
//    }
//}