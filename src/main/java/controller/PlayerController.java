package controller;

import gui_fields.GUI_Player;
import model.Player;

public class PlayerController {
    private Player playerList[];
    private GameLogic gL;
    private GUIBoundary guiB;

    public PlayerController(GUIBoundary guiB, GameLogic gL, int numberOfPlayers) {
        this.guiB = guiB;
        this.gL = gL;
        playerList = new Player[numberOfPlayers];
    }

    public void createPlayerNames() {
        String names[] = guiB.askForNames(playerList.length);

        for(int i = 0; i < playerList.length; i++){
            playerList[i] = new Player(names[i], gL.getStartBalance());
            GUI_Player piece = guiB.setUpPlayer(playerList[i].getName(), playerList[i].getBalance());
            playerList[i].setPiece(piece);
        }
    }
}
