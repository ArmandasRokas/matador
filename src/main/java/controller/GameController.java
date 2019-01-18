package controller;

import model.Cup;
import model.GameBoard;
import model.Player;
import ui.GUIBoundary;

public class GameController {
    private PlayerController plCtrl;
    private GameLogic gL;
    private GUIBoundary guiB;
    private Cup cup;
    private GameBoardController boardCtrl;
    private BankruptController bankruptCtrl;
    private PropertyController propertyCtrl;
    private ChanceCardController chanceCardCtrl;

    public GameController() {
        setupGame();
    }

    public void startGame() {
        int numberOfPlayers;
        do {
            numberOfPlayers = guiB.askForPlayerCount(gL.getMinPlayers() ,gL.getMaxPlayers());
        } while(!gL.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers, propertyCtrl, chanceCardCtrl);
        plCtrl.createPlayers();
        runGame();
    }

    private void runGame() {
        //TODO Fix kommunikation med spiller
        boolean activeGame = true;
        while (activeGame) {
            guiB.showChanceCard("");
            if (plCtrl.getIsCurrPlayerInJail()) {
                inPrison();
            } else {
                showBeforeTurnMenu();
                showAfterTurnMenu();
                Player p = gL.winnerFound(plCtrl.getPlayerList());

                if (p != null) {
                    guiB.declareWinner(p.getPlayerID());
                    activeGame = false;
                }

                if (!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt()) {
                    plCtrl.changePlayer();
                } else {
                    guiB.tellPlayerExtraTurn(plCtrl.getCurrPlayerID());
                }
            }

        }
        askForNewGame();
    }

    private void showBeforeTurnMenu(){
        boolean takenTurn = false;

        while(!takenTurn) {
            int res = guiB.takeTurn(plCtrl);
            switch (res) {
                case 0:
                    throwDices();
                    takeTurn();
                    takenTurn = true;
                    break;
                case 1:
                    buyHousing();
                    break;
            }
        }
    }

    private void showAfterTurnMenu() {
        boolean endTurn = false;

        while(!endTurn) {
            int res = guiB.endTurn(plCtrl);
            switch (res) {
                case 0:
                    endTurn = true;
                    break;
                case 1:
                    buyHousing();
                    break;
            }
        }
    }

    private void buyHousing() {
        boolean stillBuying = true;
        GameBoard gameBoard = boardCtrl.getGameBoard();
        ManageBuildingsController mbCtrl = new ManageBuildingsController(guiB, gameBoard);
        while(stillBuying) {
            int[] possibleStreets = plCtrl.getCurrPlayerSquarePossibleToBuild();
            String res = guiB.administrateProperties(possibleStreets); //FixMe Show building prices? As in: "Rødovervej - 50kr"
            switch (res) {
                case "exit": //exit
                    stillBuying = false;
                    break;
                default:
                    //køb hus (hvis spilleren har råd)
                    mbCtrl.buyHouse(plCtrl, res);
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    break;
            }
        }
    }

    private void inPrison() {
        int getOutOfJailAnswer = guiB.getOutOfJail(plCtrl);

        switch (getOutOfJailAnswer) {
            case 0:
                throwDices();
                if (cup.getEyesDie1() == cup.getEyesDie2()) {
                    plCtrl.setCurrPlayerIsInJail(false);
                    plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har slået 2 ens og er kommet ud af fængsel");
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    plCtrl.movePlayer(cup.getCurrentRollScore(), true);
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
                    plCtrl.movePlayer(cup.getCurrentRollScore(), true);
                    plCtrl.changePlayer();
                } else {
                    plCtrl.changePlayer();
                }
                break;

            case 1:
                plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har valgt at betale 50 kr for at komme ud af fængslet");
                guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                plCtrl.currPlayerMoneyInfluence(-50);
                plCtrl.setCurrPlayerIsInJail(false);
                guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance()); //FixMe
                takeTurn();
                break;
        }
    }


    private void throwDices() {
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());
    }

    private void takeTurn() {
        plCtrl.movePlayer(cup.getCurrentRollScore(), true);
        boardCtrl.actOnSquare(plCtrl);
        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
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
        guiB = new GUIBoundary();
        cup = new Cup();
        this.bankruptCtrl = new BankruptController(guiB);
        this.boardCtrl = new GameBoardController(guiB);
        this.propertyCtrl = new PropertyController(guiB, bankruptCtrl);
        this.chanceCardCtrl = new ChanceCardController(guiB, boardCtrl);
    }
}
