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

    public GameController() {
        setupGame();
    }

    public void startGame() {
        int numberOfPlayers;
        do {
            numberOfPlayers = guiB.askForPlayerCount(gL.getMinPlayers() ,gL.getMaxPlayers());
        } while(!gL.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers, propertyCtrl);
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
                showMenu();
                Player p = gL.winnerFound(plCtrl.getPlayerList());

                if (p != null) {
                    guiB.declareWinner(p.getPlayerID());
                    activeGame = false;
                }

                if (!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt()) {
                    plCtrl.changePlayer();
                }
            }

        }
        askForNewGame();
    }

    private void showMenu(){
        int res = guiB.takeTurn(plCtrl);
        switch (res) {
            case 1:
                throwDices();
                takeTurn();
                break;
            case 2:
                buyHousing();
                break;
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
        showMenu(); //FixMe KNA vil gerne have den her droppet
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

    private void throwDices() { //FixMe Skal være void?
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());
    }

    private void takeTurn() {
        plCtrl.movePlayer(cup.getCurrentRollScore());
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
//        this.boardCtrl = new GameBoardController(guiB, bankruptCtrl);
        this.propertyCtrl = new PropertyController(guiB, bankruptCtrl);
    }
}
