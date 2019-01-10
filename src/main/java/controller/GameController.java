package controller;

import model.Cup;

public class GameController {
    private PlayerController plCtrl;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Cup cup;

    public GameController() {
        gL = new GameLogic();
        guiB = new GUIBoundary();
        cup = new Cup();
    }

    public void startGame() {
//        ...loadBoard();
//        guiB.setGUIBoard(); /TODO Convert GUI_Board to our board (names, prices etc.)?

        int numberOfPlayers;
        do {
            numberOfPlayers = guiB.askForPlayerCount(gL.getMinPlayers() ,gL.getMaxPlayers());
        } while(!gL.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers);

        plCtrl.createPlayers();

        runGame();
    }

    private void runGame() {
        //TODO Fix kommunikation med spiller
        while (true) {
            int res = guiB.takeTurn();
            switch (res){
                case 1:
                    throwDices();
                    break;
            }
            //changePlayer();
        }

    }

    private void throwDices() {

        cup.roll();
        int rollScore = cup.getCurrentRollScore();
        plCtrl.movePlayer(rollScore);
     }
}
