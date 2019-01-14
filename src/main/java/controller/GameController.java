package controller;

import model.Cup;
import model.Player;
import ui.GUIBoundary;

public class GameController {
    private PlayerController plCtrl;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Cup cup;
    private GameBoardController boardCtrl;
    private BankruptController bankruptController;

    public GameController() {
    //    gL = new GameLogic();
    //    guiB = new GUIBoundary();
    //    cup = new Cup();
    //    this.bankruptController = new BankruptController(guiB);
    //    this.boardCtrl = new GameBoardController(guiB, bankruptController);
        setupGame();

    }

    public void startGame() {
//        ...loadBoard();
//        guiB.setGUIBoard(); /TODO Convert GUI_Board to our board (names, prices etc.)?
        //TODO ARM setGUIBoard(String[] gameBoardController.getSquaresNames()) in order to fix danish letters bug.
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
        boolean activeGame = true;
        while (activeGame) {
            int res = guiB.takeTurn(plCtrl);
            switch (res) {
                case 1:
                    throwDices();
                    boardCtrl.actOnSquare(plCtrl);
                    guiB.showCurrScenarioForPlayer(boardCtrl.getCurrSquareToString());
                    break;
            }
            Player p = gL.winnerFound(plCtrl.getPlayerList());
            if(p != null) {
                guiB.declareWinner(p.getPlayerID());
                activeGame = false;
            }

            if(!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt()) {
                plCtrl.changePlayer();
            }


        }

        askForNewGame();

    }


    private void throwDices() {
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());

        int rollScore = cup.getCurrentRollScore();
        plCtrl.movePlayer(rollScore);


    }

    private void askForNewGame() {
        String input = guiB.endGame();

        switch(input) {
            case "Ja":
                setupGame();
                startGame();
        }
    }

    private void setupGame(){
        gL = new GameLogic();
        guiB = new GUIBoundary(); //FixMe Ask professor if possible to shutdown/restart GUI or implement a better reset method
        cup = new Cup();
        this.bankruptController = new BankruptController(guiB);
        this.boardCtrl = new GameBoardController(guiB, bankruptController);

    }
}
