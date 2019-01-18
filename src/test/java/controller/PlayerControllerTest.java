package controller;

import model.GameBoard;
import model.Player;
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
    GameBoardController gameBoardController;
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
        playerController = new PlayerController(mockGUI, gameLogic, 3, propertyController, cardController, gameBoardController);
        playerController.createPlayers();
    }

    @Test
    void movePlayer() {
        //TODO add test if player can go in a circle correctly. Check for off-by-one bug
        Player[] players = playerController.getPlayerList();
        playerController.movePlayer(1, true);

        assertEquals(1, players[0].getCurrentPosition());
    }
}