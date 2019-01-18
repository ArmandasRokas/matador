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

        plCtrl = new PlayerController(guiB, gL, numberOfPlayers, propertyCtrl, chanceCardCtrl, boardCtrl);
        plCtrl.createPlayers();
        runGame();
    }

    private void runGame() {
        //TODO Fix kommunikation med spiller
        boolean activeGame = true;
        while (activeGame) {
            guiB.showChanceCard("");
            if (plCtrl.getIsCurrPlayerInJail()) {
                inJail();
            } else {
                showBeforeTurnMenu();
                if(!plCtrl.getIsCurrPlayerInJail()){
                    showAfterTurnMenu();
                }
                Player p = gL.winnerFound(plCtrl.getPlayerList());

                if (p != null) {
                    guiB.declareWinner(p.getPlayerID());
                    activeGame = false;
                }
                checkForExtraRoundOrChangePlayer();
//                if (!(cup.getEyesDie1() == cup.getEyesDie2()) || plCtrl.getCurrPlayer().getBankrupt() || plCtrl.getCurrPlayer().getIsCurrPlayerInJail()) {
//                    plCtrl.changePlayer();
//                } else {
//                    guiB.tellPlayerExtraTurn(plCtrl.getCurrPlayerID());
//                } //TODO Kontroller om der er noget der mangler i metoden under
            }

        }
        askForNewGame();
    }

    private void checkForExtraRoundOrChangePlayer() {
        if (plCtrl.getCurrPlayer().getBankrupt()){
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
                case 0:
                    throwDices();
                    if(plCtrl.checkForSpeeding()){
                        plCtrl.setCurrPlayerToJail();
                        takenTurn = true;
                        break;
                    }
                    takeTurn();
                    takenTurn = true;
                    break;
                case 1:
                    buyHousing();
                    break;
                case 2:
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
                case 0:
                    endTurn = true;
                    break;
                case 1:
                    buyHousing();
                    break;
                case 2:
                    sellHousing();
                    break;
            }
        }
    }

    private void buyHousing() {
        boolean stillBuying = true;
        GameBoard gameBoard = boardCtrl.getGameBoard();
        ManageBuildingsController mbCtrl = new ManageBuildingsController(guiB, gameBoard);
        while(stillBuying) {
            int[] possibleStreets = mbCtrl.getCurrPlayerSquarePossibleToBuild(plCtrl);
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

    private void sellHousing(){
        boolean stillSelling = true;
        GameBoard gameBoard = boardCtrl.getGameBoard();
        ManageBuildingsController mbCtrl = new ManageBuildingsController(guiB, gameBoard);
        while(stillSelling) {
            int[] possibleStreets = mbCtrl.getCurrPlayerSquarePossibleToSellHousing(plCtrl);
            String res = guiB.administrateProperties(possibleStreets); //FixMe Show building prices? As in: "Rødovervej - 50kr"
            switch (res) {
                case "exit": //exit
                    stillSelling = false;
                    break;
                default:
                    //sælge hus
                    mbCtrl.sellHouse(plCtrl, res);
                    guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
                    break;
            }
        }
    }

    private void inJail() {
        int getOutOfJailAnswer = guiB.getOutOfJail(plCtrl);

        switch (getOutOfJailAnswer) {
            case 0:
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
                    guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance()); //TODO Kontrol af fallit
                    getOutOfPrison(plCtrl.getCurrPlayerName() + " har betalt 50kr for at komme ud af fængsel da du ikke har kunne slå sig selv ud. Du rykker "
                            + cup.getCurrentRollScore() + " felter.");
                    takeTurn();
                    plCtrl.changePlayer();
                } else if (cup.getEyesDie1() != cup.getEyesDie2()){
                    plCtrl.changePlayer();
                }
                break;

            case 1:
                plCtrl.currPlayerMoneyInfluence(-50);
                getOutOfPrison(plCtrl.getCurrPlayerName() + " har valgt at betale 50 kr for at komme ud af fængslet");
                guiB.updateBalance(plCtrl.getCurrPlayerID(), plCtrl.getCurrPlayerBalance()); //FixMe
                break;

            case 2:
                plCtrl.useGetOutOfJailCard();
                getOutOfPrison(plCtrl.getCurrPlayerName() + " at bruge deres kort fra Kongen til at komme ud, velkommen tilbage til spillet!");
                break;
        }
    }

    public void getOutOfPrison(String message) {
        plCtrl.setCurrScenarioForPlayer(message);
        guiB.showCurrScenarioForPlayer(plCtrl.getCurrScenarioForPlayer());
        plCtrl.setCurrPlayerIsInJail(false);
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
