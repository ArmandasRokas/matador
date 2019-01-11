package controller;

import model.Player;
import org.junit.jupiter.api.Test;
import ui.MockGUI;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    @Test
    void movePlayer() {
        PlayerController plCtrl = new PlayerController(new MockGUI(), new GameLogic(), 3);
        plCtrl.createPlayers();

        Player[] players = plCtrl.getPlayerList();
        plCtrl.movePlayer(1);

        assertEquals(1, players[0].getCurrentPosition());
    }
}