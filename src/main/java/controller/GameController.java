package controller;

import model.Cup;
import model.GameBoard;
import model.Player;
import ui.GUIBoundary;

public class GameController {
    private PlayerController plCtrl;
    private GameRules gameRules;
    private GUIBoundary guiB;
    private Cup cup;
    private GameBoardController boardCtrl;
    private PropertyController propertyCtrl;
    private ChanceCardController chanceCardCtrl;

    public GameController() {
        setupGame();
    }

    private void setupGame() {
        gameRules = new GameRules();
        guiB = new GUIBoundary();
        cup = new Cup();
        BankruptController bankruptCtrl = new BankruptController(guiB);
        this.boardCtrl = new GameBoardController(guiB);
        this.propertyCtrl = new PropertyController(guiB, bankruptCtrl, cup);
        this.chanceCardCtrl = new ChanceCardController(guiB, boardCtrl);
    }

    public void startGame() {
        int numberOfPlayers;
        do {
            numberOfPlayers = guiB.askForPlayerCount(gameRules.getMinPlayers() , gameRules.getMaxPlayers());
        } while(!gameRules.controlPlayerCount(numberOfPlayers));

        plCtrl = new PlayerController(guiB, gameRules, numberOfPlayers, propertyCtrl, chanceCardCtrl, boardCtrl);
        plCtrl.createPlayers();
        runGame();
    }

    private void runGame() {
        boolean activeGame = true;

        while (activeGame) {
            guiB.showChanceCard("");
            if (plCtrl.getIsCurrPlayerInJail()) {
                inJail();
            } else {
                showBeforeTurnMenu();
                if(!plCtrl.getIsCurrPlayerInJail() && !plCtrl.getCurrPlayer().isBankrupt()){
                    showAfterTurnMenu();
                }
                Player p = gameRules.winnerFound(plCtrl.getPlayerList());

                if (p != null) {
                    guiB.declareWinner(p.getPlayerID());
                    activeGame = false;
                }
                checkForExtraRoundOrChangePlayer();
            }
        }
        askForNewGame();
    }

    private void checkForExtraRoundOrChangePlayer() {
        if (plCtrl.getCurrPlayer().isBankrupt()){
            plCtrl.changePlayer();
            plCtrl.resetCurrPlayerExtraTurnCount();
        } else if (cup.getEyesDie1() != cup.getEyesDie2()) {
            plCtrl.changePlayer();
            plCtrl.resetCurrPlayerExtraTurnCount();
        } else if (cup.getEyesDie1() == cup.getEyesDie2() && plCtrl.getCurrPlayerExtraTurnCount() < 3){
            guiB.tellPlayerExtraTurn(plCtrl.getCurrPlayerID());
        } else if (cup.getEyesDie1() == cup.getEyesDie2() && plCtrl.getCurrPlayerExtraTurnCount() == 3){
            plCtrl.changePlayer();
            plCtrl.resetCurrPlayerExtraTurnCount();
        }
    }

    private void showBeforeTurnMenu(){
        boolean takenTurn = false;

        while(!takenTurn) {
            int res = guiB.takeTurn(plCtrl);
            switch (res) {
                case 0: //Take turn
                    throwDices();
                    if(plCtrl.checkForSpeeding()){
                        plCtrl.setCurrPlayerToJail();
                        takenTurn = true;
                        break;
                    }
                    takeTurn();
                    takenTurn = true;
                    break;
                case 1: //Open buy housing menu
                    buyHousing();
                    break;
                case 2: //Open sell housing menu
                    sellHousing();
                    break;
            }
        }
    }

    private void showAfterTurnMenu() {
        boolean endTurn = false;

        while(!endTurn) {
            int res = guiB.endTurn(plCtrl);
            switch (res) {
                case 0: //End turn
                    endTurn = true;
                    break;
                case 1: //Open buy housing menu
                    buyHousing();
                    break;
                case 2: //Open sell housing menu
                    sellHousing();
                    break;
            }
        }
    }

    private void buyHousing() {
        boolean stillBuying = true;
        GameBoard gameBoard = boardCtrl.getGameBoard();
        ManageBuildingsController mbCtrl = new ManageBuildingsController(guiB, gameRules, gameBoard);

        while(stillBuying) {
            int[] possibleStreets = mbCtrl.getCurrPlayerSquarePossibleToBuild(plCtrl);
            String res = guiB.administrateProperties(possibleStreets);

            switch (res.toLowerCase()) {
                case "exit": //Exit menu
                    stillBuying = false;
                    break;
                default:
                    mbCtrl.buyHouse(plCtrl, res);
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    break;
            }
        }
    }

    private void sellHousing(){
        boolean stillSelling = true;
        GameBoard gameBoard = boardCtrl.getGameBoard();
        ManageBuildingsController mbCtrl = new ManageBuildingsController(guiB, gameRules, gameBoard);

        while(stillSelling) {
            int[] possibleStreets = mbCtrl.getCurrPlayerSquarePossibleToSellHousing(plCtrl);
            String res = guiB.administrateProperties(possibleStreets);

            switch (res.toLowerCase()) {
                case "exit": //Exit menu
                    stillSelling = false;
                    break;
                default:
                    mbCtrl.sellHouse(plCtrl, res);
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    break;
            }
        }
    }

    private void inJail() {
        int getOutOfJailAnswer = guiB.getOutOfJail(plCtrl);

        switch (getOutOfJailAnswer) {
            case 0: //Throw to get out of jail
                throwDices();
                if (cup.getEyesDie1() == cup.getEyesDie2()) {
                    getOutOfPrison(plCtrl.getCurrPlayerName() + " har slået 2 ens og er kommet ud af fængsel");
                    takeTurn();
                } else if (cup.getEyesDie1() != cup.getEyesDie2()) {
                    plCtrl.getCurrPlayer().increaseTurnsTakenInJail();
                    plCtrl.setCurrScenarioForPlayer(plCtrl.getCurrPlayerName() + " har desværre ikke slået 2 ens, du må blive i fængsel. Det var dit " +
                            plCtrl.getCurrPlayer().getTurnsTakenInJail() + ". forsøg!");
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                }

                if (plCtrl.getCurrPlayer().getTurnsTakenInJail() >= 3) {
                    plCtrl.currPlayerMoneyInfluence(-50);
                    guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
                    getOutOfPrison(plCtrl.getCurrPlayerName() + " har betalt 50kr for at komme ud af fængsel da du ikke har kunne slå sig selv ud. Du rykker "
                            + cup.getCurrentRollScore() + " felter.");
                    takeTurn();
                    plCtrl.changePlayer();
                } else if (cup.getEyesDie1() != cup.getEyesDie2()){
                    plCtrl.changePlayer();
                }
                break;

            case 1: //Pay to get out of jail
                plCtrl.currPlayerMoneyInfluence(-50);
                getOutOfPrison(plCtrl.getCurrPlayerName() + " har valgt at betale 50 kr for at komme ud af fængslet");
                guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance());
                break;

            case 2: //Use card to get out of jail
                plCtrl.useGetOutOfJailCard();
                getOutOfPrison(plCtrl.getCurrPlayerName() + " at bruge deres kort fra Kongen til at komme ud, velkommen tilbage til spillet!");
                break;
        }
    }

    private void askForNewGame() {
        int input = guiB.endGame();

        switch (input) {
            case 0: //New game
                setupGame();
                startGame();
        }
    }

    private void throwDices() {
        cup.roll();
        guiB.setDices(cup.getEyesDie1(), cup.getEyesDie2());
        if (cup.getEyesDie1() == cup.getEyesDie2()) {
            plCtrl.addOneCurrPlayerExtraTurnCount();
        }
    }

    private void takeTurn() {
        plCtrl.movePlayer(cup.getCurrentRollScore(), true);
        boardCtrl.actOnSquare(plCtrl);
        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
    }

    public void getOutOfPrison(String message) {
        plCtrl.setCurrScenarioForPlayer(message);
        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
        plCtrl.setCurrPlayerIsInJail(false);
    }
}