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
        if(plCtrl.getIsCurrPlayerInJail()) {
            int getOutOfJailAnswer = guiB.getOutOfJail(plCtrl);
            switch (getOutOfJailAnswer){
                case 1:
                    if(plCtrl.getCurrPlayer().getTurnsTakenInJail() < 3) {
                        throwDicesInJail();
                        if (cup.getEyesDie1() == cup.getEyesDie2()) {
                            plCtrl.setCurrPlayerIsInJail(false);
                            plCtrl.getCurrPlayer().resetTurnsTakenInJail();
                            guiB.tellPlayer(plCtrl.getCurrPlayerName() + " har slået 2 ens og er kommet ud af fængsel");
                            int rollScore = cup.getCurrentRollScore();
                            plCtrl.movePlayer(rollScore);

                        } else if(cup.getEyesDie1() != cup.getEyesDie2()) {
                            guiB.tellPlayer(plCtrl.getCurrPlayerName() + " har desværre ikke slået 2 ens, du må blive i fængsel");
                            plCtrl.getCurrPlayer().increaseTurnsTakenInJail();
                            guiB.tellPlayer(plCtrl.getCurrPlayerName() + " har brugt " + plCtrl.getCurrPlayer().getTurnsTakenInJail() + " ud af sine 3 forsøg, for at prøve at slå 2 ens");

                        }    if(plCtrl.getCurrPlayer().getTurnsTakenInJail() > 2) {
                            plCtrl.setCurrPlayerIsInJail(false);
                            plCtrl.getCurrPlayer().resetTurnsTakenInJail();
                            plCtrl.currPlayerMoneyInfluence(-50);
                            guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
                            guiB.tellPlayer(plCtrl.getCurrPlayerName() + " har betalt 50 kr for at komme ud af fængsel, efter 3 mislykkedes terningekast");
                            int rollScore = cup.getCurrentRollScore();
                            guiB.tellPlayer(plCtrl.getCurrPlayerName() + " rykker " + cup.getCurrentRollScore() + " felter, og giver turen videre");
                            plCtrl.movePlayer(rollScore);

                            plCtrl.changePlayer();

                        }   else  { plCtrl.changePlayer(); }

                        }
                break;

                case 2:
                    guiB.tellPlayer(plCtrl.getCurrPlayerName() + " har valgt at betale 50 kr for at komme ud af fængslet");
                    plCtrl.currPlayerMoneyInfluence(-50);
                    plCtrl.setCurrPlayerIsInJail(false);
                    plCtrl.getCurrPlayer().resetTurnsTakenInJail();
                    guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());

                    break;

            }
        }else {
            int res = guiB.takeTurn(plCtrl);
            switch (res) {
                case 1:

                        throwDices();
                        boardCtrl.actOnSquare(plCtrl);
                        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                        break;


            }
            Player p = gL.winnerFound(plCtrl.getPlayerList());
            if(p != null) {
                guiB.declareWinner(p.getPlayerID());
                activeGame = false;
            }

            if(!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt() || plCtrl.getCurrPlayer().getIsCurrPlayerInJail()) {
                plCtrl.changePlayer();
            }

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

    private void throwDicesInJail() {
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());

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

        //        ...loadBoard();
//        guiB.setGUIBoard(); /TODO Convert GUI_Board to our board (names, prices etc.)?
        //TODO ARM setGUIBoard(String[] gameBoardController.getSquaresNames()) in order to fix danish letters bug.

//        guiB.setUpGUIFields(boardCtrl.getSquareNames());
    }
}
