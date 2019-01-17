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
            numberOfPlayers = guiB.askForPlayerCount(gL.getMinPlayers(), gL.getMaxPlayers());
        } while (!gL.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers);

        plCtrl.createPlayers();

        runGame();
    }

    private void runGame() {
        //TODO Fix kommunikation med spiller
        boolean activeGame = true;
        while (activeGame) {

            if (plCtrl.getIsCurrPlayerInJail()) {
                inPrison();
            } else {
                int res = guiB.takeTurn(plCtrl);
                switch (res) {
                    case 1:
                        throwDices();
                        takeTurn();
                        break;
                }
                Player p = gL.winnerFound(plCtrl.getPlayerList());
                if (p != null) {
                    guiB.declareWinner(p.getPlayerID());
                    activeGame = false;
                }
                if (!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt() || plCtrl.getCurrPlayer().getIsCurrPlayerInJail()) {
                    plCtrl.changePlayer();
                }
            }
        }
        askForNewGame();
    }

    private void takeTurn() {
        plCtrl.movePlayer(cup.getCurrentRollScore());
        boardCtrl.actOnSquare(plCtrl);
        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
    }

    private void inPrison() {
        int getOutOfJailAnswer = guiB.getOutOfJail(plCtrl);

        switch (getOutOfJailAnswer) {
            case 1:
                    throwDices();
                    if (cup.getEyesDie1() == cup.getEyesDie2()) {
                        plCtrl.setCurrPlayerIsInJail(false);
                        plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har slået 2 ens og er kommet ud af fængsel");
                        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                        plCtrl.movePlayer(cup.getCurrentRollScore());
                    } else if (cup.getEyesDie1() != cup.getEyesDie2()) {
                        plCtrl.getCurrPlayer().increaseTurnsTakenInJail();
                        plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har desværre ikke slået 2 ens, du må blive i fængsel. Det var dit " +
                                plCtrl.getCurrPlayer().getTurnsTakenInJail() + ". forsøg!");
                        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    }

                    if (plCtrl.getCurrPlayer().getTurnsTakenInJail() >= 3) {
                        plCtrl.setCurrPlayerIsInJail(false);
                        plCtrl.currPlayerMoneyInfluence(-50);
                        guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance()); //TODO Kontrol af fallit
                        plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har betalt 50kr for at komme ud af fængsel da du ikke har kunne slå sig selv ud. Du rykker "
                                + cup.getCurrentRollScore() + " felter.");
                        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                        plCtrl.movePlayer(cup.getCurrentRollScore());
                        plCtrl.changePlayer();
                    } else {
                        plCtrl.changePlayer();
                    }
                break;

            case 2:
                plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har valgt at betale 50 kr for at komme ud af fængslet");
                guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                plCtrl.currPlayerMoneyInfluence(-50);
                plCtrl.setCurrPlayerIsInJail(false);
                guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance()); //FixMe
                takeTurn();
                break;
        }
    }

    private int throwDices() {
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());
        return cup.getCurrentRollScore();
    }

    private void askForNewGame() {
        String input = guiB.endGame();

        switch (input) {
            case "Ja":
                setupGame();
                startGame();
        }
    }

    private void setupGame() {
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
