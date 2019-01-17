//package controller;
//
//import model.GameBoard;
//import model.Player;
//import model.square.property.StreetSquare;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ui.MockGUI;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PlayerControllerTest {
//    MockGUI mockGUI ;
//    GameLogic gameLogic ;
//    PlayerController playerController;
//    BankruptController bankruptController;
//    PropertyController propertyController;
//    ChanceCardController cardController;
//    GameBoard gameBoard ;
//    @BeforeEach
//    void setUp(){
//        mockGUI = new MockGUI();
//        gameLogic = new GameLogic();
//        playerController = new PlayerController(mockGUI, gameLogic, 3);
//        playerController.createPlayers();
//        bankruptController = new BankruptController(mockGUI);
//        propertyController = new PropertyController(mockGUI,bankruptController);
//        cardController = new ChanceCardController(mockGUI);
//        gameBoard = new GameBoard();
//    }
//
//    @Test
//    void movePlayer() {
//        //TODO add test if player can go in a circle correctly. Check for off-by-one bug
//        PlayerController plCtrl = new PlayerController(new MockGUI(), new GameLogic(), 3);
//        plCtrl.createPlayers();
//
//        Player[] players = plCtrl.getPlayerList();
//        plCtrl.movePlayer(1);
//
//        assertEquals(1, players[0].getCurrentPosition());
//    }
//
//
////    @Test
////    void getCurrPlayerSquarePossibleToBuildTest(){
////        //Arrange
////        StreetSquare rødovrevej = (StreetSquare) gameBoard.getSquareList()[1];
////        StreetSquare hvidovrevej = (StreetSquare) gameBoard.getSquareList()[3];
////        //Act
////
////        propertyController.buyProperty(rødovrevej, playerController);
////        propertyController.buyProperty(hvidovrevej, playerController);
////        StreetSquare[] streetSquaresPossibleToBuild = playerController.getCurrPlayerSquarePossibleToBuild();
////
////        boolean isFirstSquareOwned = false;
////        boolean isSecondSquareOwned = false;
////
////
////        for(StreetSquare street: streetSquaresPossibleToBuild){
////
////            if(rødovrevej.equals(street)){
////                isFirstSquareOwned = true;
////            } else if(hvidovrevej.equals(street)){
////                isSecondSquareOwned= true;
////            }
////        }
////
////        //Assert
////        assertTrue(isFirstSquareOwned);
////        assertTrue(isSecondSquareOwned);
////
////    }
//}