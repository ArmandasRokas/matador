package controller;

import model.ChanceCard;
import model.chanceCards.GetOutOfJailCC;
import model.chanceCards.MoneyInfluenceCC;
import model.chanceCards.MovePlayer3SquaresBackCC;
import model.chanceCards.MovePlayerToSquareCC;
import model.square.ChanceSquare;
import ui.GUIBoundary;

import java.util.Random;

public class ChanceCardController {
    private GUIBoundary guiBoundary;
    private ChanceCard cardDeck[];

    public ChanceCardController(GUIBoundary guiBoundary, GameBoardController boardCtrl){

        this.guiBoundary = guiBoundary;

        createDeck();
    }

    private void createDeck() {
        cardDeck = new ChanceCard[26];

        cardDeck[0] = new MovePlayer3SquaresBackCC("Ryk tre felter tilbage.", 3);
        cardDeck[1] = new MovePlayer3SquaresBackCC("Ryk tre felter tilbage.", 3);
        cardDeck[2] = new GetOutOfJailCC("I anledning af Kongsens fødselsdag får de lov til at være kriminel én gang. Behold dette kort indtil De får brug for det.");
        cardDeck[3] = new GetOutOfJailCC("I anledning af Kongsens fødselsdag får de lov til at være kriminel én gang. Behold dette kort indtil De får brug for det.");
        cardDeck[4] = new MovePlayerToSquareCC("Tag med Øresundsbåden - Hvis de passerer 'Start' indkasserer de 200kr", false);
        cardDeck[5] = new MovePlayerToSquareCC("Tag in på Rådhuspladsen", false);
        cardDeck[6] = new MovePlayerToSquareCC("Ryk frem til Frederiksbergálle . Hvis de passerer 'Start' indkasserer de 200kr", false);
        cardDeck[7] = new MovePlayerToSquareCC("Ryk frem til Grønningen. Hvis de passerer 'Start' indkasserer de 200kr", false);
        cardDeck[8] = new MovePlayerToSquareCC("Ryk frem til 'Start'", false);
        cardDeck[9] = new MovePlayerToSquareCC("Gå i fængsel. De rykkes direkte til fængsel. Selv om de passerer 'Start' indkasserer de ikke 200kr", true);
        cardDeck[10] = new MovePlayerToSquareCC("Gå i fængsel. De rykkes direkte til fængsel. Selv om de passerer 'Start' indkasserer de ikke 200kr", true);
        cardDeck[11] = new MoneyInfluenceCC("De har fået en parkeingsbøde. Betal 60kr til banken", -60);
        cardDeck[12] = new MoneyInfluenceCC("Grundet på dyrtiden har De fået gageforhøjelse, Modtag 50kr", 50);
        cardDeck[13] = new MoneyInfluenceCC("Deres præmieobligation er kommet ud. De modtager 100kr", 100);
        cardDeck[14] = new MoneyInfluenceCC("Efter auktionen på Assistentshuset, hvor De havde pantsat Deres tøj, modtager de 108kr", 108);
        cardDeck[15] = new MoneyInfluenceCC("De harrettidigt afleveret Deres abonnementskort. De modtager deres depositum på 5kr", 5);
        cardDeck[16] = new MoneyInfluenceCC("De har kørt frem for 'Fuld Stop' og skal betale en bøde på 150kr", -150);
        cardDeck[17] = new MoneyInfluenceCC("De har anskaffet et nyt dæk til deres vogn. Betal 120kr", -120);
        cardDeck[18] = new MoneyInfluenceCC("Betal for vognvask og smøring. Betal 15kr", -15);
        cardDeck[19] = new MoneyInfluenceCC("Betal 125kr for modtagne 2 kasser øl.", -125);
        cardDeck[20] = new MoneyInfluenceCC("Manufakturvarerne er blevet billigere og bedre. De sparer 100kr", 100);
        cardDeck[21] = new MoneyInfluenceCC("Modtag udbytte af Deres aktier på 50kr", 50);
        cardDeck[22] = new MoneyInfluenceCC("De har været en tur i udlandet og haft for mange cigaretter med hjem. Betal tolden på 50kr", -50);
        cardDeck[23] = new MoneyInfluenceCC("De har solgt Deres gamle klude. Modtag 20kr", 20);
        cardDeck[24] = new MoneyInfluenceCC("Kommunen har eftergivet et kvartals skat. Hæv i banken til en glad aften 200kr", 200);

        shuffleDeck();
    }

    private void shuffleDeck() {
        ChanceCard[] newDeck = new ChanceCard[cardDeck.length];
        Random r = new Random();

        for(ChanceCard chanceCard : cardDeck) {
            boolean cardPlaced = false;

            while(!cardPlaced) {
                int randomPlacement = r.nextInt(cardDeck.length);

                if(newDeck[randomPlacement] == null) {
                    newDeck[randomPlacement] = chanceCard;
                    cardPlaced = true;
                }
            }
        }
        System.out.println("");
        this.cardDeck = newDeck;
    }

    public void handleChanceSquare(ChanceSquare chanceSquare, PlayerController playerController) {

    }


}
