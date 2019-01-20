package controller;

import model.Cup;
import model.GameBoard;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import UI.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {
    MockGUI mockGUI ;
    GameRules gameRules;
    PlayerController playerController;
    BankruptController bankruptCtrl;
    PropertyController propertyController;
    GameBoardController gameBoardCtrl;
    ChanceCardController cardController;
    GameBoardController gameBoardController;
    GameBoard gameBoard ;
    Cup cup;

    @BeforeEach
    void setUp(){
        mockGUI = new MockGUI();
        gameRules = new GameRules();
        bankruptCtrl = new BankruptController(mockGUI);
        gameBoardCtrl = new GameBoardController(mockGUI);
        cardController = new ChanceCardController(mockGUI, gameBoardCtrl, bankruptCtrl);
        gameBoard = new GameBoard();
        cup = new Cup();
        propertyController = new PropertyController(mockGUI, bankruptCtrl, cup);
        playerController = new PlayerController(mockGUI, gameRules, 3, propertyController, cardController, gameBoardCtrl, bankruptCtrl);
        playerController.createPlayers();
    }

    @Test
    void movePlayer() {
        Player[] players = playerController.getPlayerList();
        playerController.movePlayer(1, true);

        assertEquals(1, players[0].getCurrentPosition());
    }
}