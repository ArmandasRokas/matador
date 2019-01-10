package controller;

public class GameController {
    private PlayerController plCtrl;
    private GameLogic gL;
    private GUIBoundary guiB;

    public GameController() {
        gL = new GameLogic();
        guiB = new GUIBoundary();
    }

    public void startGame() {
//        ...loadBoard();
//        guiB.setGUIBoard(); /TODO Convert GUI_Board to our board (names, prices etc.)

        int numberOfPlayers;
        do {
            numberOfPlayers = guiB.askForPlayerCount(gL.getMinPlayers() ,gL.getMaxPlayers());
        } while(!gL.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers);

        plCtrl.createPlayers();

//        runGame();
    }
}
