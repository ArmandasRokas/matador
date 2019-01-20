package controller;

import model.chanceCard.ChanceCard;
import model.chanceCard.GetOutOfJailCC;
import model.chanceCard.MoneyInfluenceCC;
import model.chanceCard.MovePlayer3SquaresBackCC;
import model.chanceCard.MovePlayerToSquareCC;
import ui.GUIBoundary;

import java.util.Random;

public class ChanceCardController {
    private GUIBoundary guiB;
    private GameBoardController gameBoardCtrl;
    private BankruptController bankruptCtrl;
    private ChanceCard cardDeck[];
    private int cardsPicked;

    public ChanceCardController(GUIBoundary guiB, GameBoardController gameBoardCtrl, BankruptController bankruptCtrl) {
        this.guiB = guiB;
        this.gameBoardCtrl = gameBoardCtrl;
        this.bankruptCtrl = bankruptCtrl;
        createDeck();
        cardsPicked = 0;
    }

    private void shuffleDeck() {
        ChanceCard[] newDeck = new ChanceCard[cardDeck.length];
        Random r = new Random();

        for (ChanceCard chanceCard : cardDeck) {
            boolean cardPlaced = false;

            while (!cardPlaced) {
                int randomPlacement = r.nextInt(cardDeck.length);

                if (newDeck[randomPlacement] == null) {
                    newDeck[randomPlacement] = chanceCard;
                    cardPlaced = true;
                }
            }
        }
        this.cardDeck = newDeck;
    }

    public void handleChanceCards(PlayerController playerCtrl) {
        ChanceCard chanceCard = pickCard();
        playerCtrl.setCurrScenarioForPlayer("Du har trukket en chancekort");
        chanceCard.pickedCard(this, playerCtrl);
    }

    public void handleChanceCard(GetOutOfJailCC chanceCard, PlayerController playerCtrl) {
        guiB.showChanceCard(chanceCard.getCardText());
        playerCtrl.giveOutOfJailCard();
        chanceCard.setInDeck(false);
    }

    public void handleChanceCard(MoneyInfluenceCC chanceCard, PlayerController playerCtrl) {
        guiB.showChanceCard(chanceCard.getCardText());

        if(!bankruptCtrl.playerCanPay(playerCtrl, chanceCard.getMoneyInfluence())) {    //Not able to pay  for chance card.
            guiB.showCurrScenarioForPlayer(playerCtrl.getCurrPlayerName() + " har ikke penge nok til at betale " + chanceCard.getMoneyInfluence() + "kr.");
            bankruptCtrl.goBankrupt(playerCtrl);
        } else {    //Able to pay for chance card.
            playerCtrl.currPlayerMoneyInfluence(chanceCard.getMoneyInfluence());
        }
    }

    public void handleChanceCard(MovePlayerToSquareCC chanceCard, PlayerController playerCtrl) {
        guiB.showChanceCard(chanceCard.getCardText());
        guiB.showCurrScenarioForPlayer(playerCtrl.getCurrScenarioForPlayer());
        playerCtrl.movePlayerToSquare(chanceCard.getSquarePosition(), chanceCard.isToPrison());
        playerCtrl.setCurrPlayerIsInJail(chanceCard.isToPrison());
        gameBoardCtrl.actOnSquare(playerCtrl);
    }

    public void handleChanceCard(MovePlayer3SquaresBackCC chanceCard, PlayerController playerCtrl) {
        guiB.showChanceCard(chanceCard.getCardText());
        guiB.showCurrScenarioForPlayer(playerCtrl.getCurrScenarioForPlayer());
        playerCtrl.movePlayer(-3, false);
        gameBoardCtrl.actOnSquare(playerCtrl);
    }

    private void createDeck() {
        cardDeck = new ChanceCard[25];

        cardDeck[0] = new MovePlayer3SquaresBackCC("Ryk tre felter tilbage.", 3);
        cardDeck[1] = new MovePlayer3SquaresBackCC("Ryk tre felter tilbage.", 3);
        cardDeck[2] = new GetOutOfJailCC("I anledning af Kongsens fødselsdag får de lov til at være kriminel én gang. De beholder dette kort indtil De får brug for det.");
        cardDeck[3] = new GetOutOfJailCC("I anledning af Kongsens fødselsdag får de lov til at være kriminel én gang. De beholder dette kort indtil De får brug for det.");
        cardDeck[4] = new MovePlayerToSquareCC("Tag med Øresundsbåden - Hvis de passerer 'Start' indkasserer de 200kr", false, 5);
        cardDeck[5] = new MovePlayerToSquareCC("Tag in på Rådhuspladsen", false, 39);
        cardDeck[6] = new MovePlayerToSquareCC("Ryk frem til Frederiksbergálle . Hvis de passerer 'Start' indkasserer de 200kr", false, 11);
        cardDeck[7] = new MovePlayerToSquareCC("Ryk frem til Grønningen. Hvis de passerer 'Start' indkasserer de 200kr", false, 24);
        cardDeck[8] = new MovePlayerToSquareCC("Ryk frem til 'Start'", false, 0);
        cardDeck[9] = new MovePlayerToSquareCC("Gå i fængsel. De rykkes direkte til fængsel. Selv om de passerer 'Start' indkasserer de ikke 200kr", true, 10);
        cardDeck[10] = new MovePlayerToSquareCC("Gå i fængsel. De rykkes direkte til fængsel. Selv om de passerer 'Start' indkasserer de ikke 200kr", true, 10);
        cardDeck[11] = new MoneyInfluenceCC("De har fået en parkeingsbøde. De har betalt 60kr til banken", -60);
        cardDeck[12] = new MoneyInfluenceCC("Grundet på dyrtiden har De fået gageforhøjelse, De har modtaget 50kr", 50);
        cardDeck[13] = new MoneyInfluenceCC("Deres præmieobligation er kommet ud. De har modtaget 100kr", 100);
        cardDeck[14] = new MoneyInfluenceCC("Efter auktionen på Assistentshuset, hvor De havde pantsat Deres tøj, og modtaget de 108kr", 108);
        cardDeck[15] = new MoneyInfluenceCC("De har rettidigt afleveret Deres abonnementskort. De har modtaget deres depositum på 5kr", 5);
        cardDeck[16] = new MoneyInfluenceCC("De har kørt frem for 'Fuld Stop' og har betalt en bøde på 150kr", -150);
        cardDeck[17] = new MoneyInfluenceCC("De har anskaffet et nyt dæk til deres vogn. De har betalt 120kr", -120);
        cardDeck[18] = new MoneyInfluenceCC("Betal for vognvask og smøring. De har betalt 15kr", -15);
        cardDeck[19] = new MoneyInfluenceCC("De har betalt 125kr for modtagne 2 kasser øl.", -125);
        cardDeck[20] = new MoneyInfluenceCC("Manufakturvarerne er blevet billigere og bedre. De har sparet 100kr", 100);
        cardDeck[21] = new MoneyInfluenceCC("De modtager udbytte af Deres aktier på 50kr", 50);
        cardDeck[22] = new MoneyInfluenceCC("De har været en tur i udlandet og haft for mange cigaretter med hjem. De har betalt tolden på 50kr", -50);
        cardDeck[23] = new MoneyInfluenceCC("De har solgt Deres gamle klude. De har modtaget 20kr", 20);
        cardDeck[24] = new MoneyInfluenceCC("Kommunen har eftergivet et kvartals skat. De har hævet 200kr i banken til en glad aften", 200);

        shuffleDeck();
    }

    private ChanceCard pickCard() {
        if (cardsPicked == cardDeck.length) {
            shuffleDeck();
            cardsPicked = 0;
        }
        return cardDeck[cardsPicked++];
    }
}