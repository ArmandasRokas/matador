package model;


import controller.ChanceCardController;
import controller.PropertyController;
import model.square.*;
import model.square.property.Company;
import model.square.property.StreetSquare;
import model.square.property.Transport;

public class GameBoard {


    private Square[] squareList;

    public GameBoard(PropertyController propertyController, ChanceCardController cardController) {

        squareList = new Square[40];
        setBoard(propertyController, cardController);



    }

    public Square[] getSquareList() {
        return squareList;
    }

    private void setBoard(PropertyController propertyController, ChanceCardController cardController) {

        squareList[0] = new StartSquare("Start", 0);
        squareList[1] = new StreetSquare("Rødovrevej", new int[]{2, 10, 30, 90, 160, 250}, 60, 0, 1, 1, propertyController);
        squareList[2] = new ChanceSquare("Prøv lykken", 2, cardController);
        squareList[3] = new StreetSquare("Hvidovrevej", new int[]{4, 20, 60, 180, 320, 540}, 60, 0, 3, 1, propertyController);
        squareList[4] = new IncomeTax("Indkomstskat", 4);
        squareList[5] = new Transport("Øresund",  new int []{25, 50, 100, 200},200,8,5,3, propertyController);
        squareList[6] = new StreetSquare("Roskildevej", new int[]{6, 30, 90, 270, 400, 550}, 100, 1, 6, 2, propertyController);
        squareList[7] = new ChanceSquare("Prøv lykken", 7, cardController);
        squareList[8] = new StreetSquare("Valby Langgade", new int[]{6, 30, 90, 270, 400, 550}, 100, 1, 8, 2, propertyController);
        squareList[9] = new StreetSquare("Allégade", new int[]{8, 40, 100, 300, 450, 600}, 120, 1, 9, 2, propertyController);
        squareList[10] = new Jail("'På besøg'", 10);
        squareList[11] = new StreetSquare("Frederiksberg Allé", new int[]{10, 50, 150, 450, 625, 750}, 140, 2, 11, 2, propertyController);
        squareList[12] = new Company("Tuborg",new int[]{4, 10},150,9,12,1,  propertyController);
        squareList[13] = new StreetSquare("Bülowsvej", new int[]{10, 50, 150, 450, 625, 750}, 140, 2, 13, 2, propertyController);
        squareList[14] = new StreetSquare("Gl. Kongevej", new int[]{12, 60, 180, 500, 700, 900}, 160, 2, 14, 2, propertyController);
        squareList[15] = new Transport("D.F.D.S", new int []{25, 50, 100, 200},200,8,15,3, propertyController);
        squareList[16] = new StreetSquare("Bernstoffsvej", new int[]{14, 70, 200, 550, 750, 950}, 180, 3, 16, 2, propertyController);
        squareList[17] = new ChanceSquare("Prøv lykken", 17, cardController);
        squareList[18] = new StreetSquare("Hellerupvej", new int[]{14, 70, 200, 550, 750, 950}, 180, 3, 18, 2, propertyController);
        squareList[19] = new StreetSquare("Strandvejen", new int[]{16, 80, 220, 600, 800, 1000}, 200, 3, 19, 2, propertyController);
        squareList[20] = new Parking("'Helle'", 20);
        squareList[21] = new StreetSquare("Trianglen", new int[]{18, 90, 250, 700, 875, 1050}, 220, 4, 21, 2, propertyController);
        squareList[22] = new ChanceSquare("Prøv lykken", 22, cardController);
        squareList[23] = new StreetSquare("Østerbrogade", new int[]{18, 90, 250, 700, 875, 1050}, 220, 4, 23, 2, propertyController);
        squareList[24] = new StreetSquare("Grønningen", new int[]{20, 100, 300, 750, 925, 1100}, 240, 4, 24, 2, propertyController);
        squareList[25] = new Transport("Ø.K.", new int []{25, 50, 100, 200},200,8,25,3,propertyController );
        squareList[26] = new StreetSquare("Bredgade", new int[]{22, 110, 330, 800, 975, 1150}, 260, 5, 26, 2, propertyController);
        squareList[27] = new StreetSquare("Kgs. Nytorv", new int[]{22, 110, 330, 800, 975, 1050}, 260, 5, 27, 2, propertyController);
        squareList[28] = new Company("Carlsberg", new int[]{4, 10}, 150, 9,28,1, propertyController);
        squareList[29] = new StreetSquare("Østergade", new int[]{22, 120, 360, 850, 1025, 1200}, 280, 5, 29, 2, propertyController);
        squareList[30] = new ToJail("De sætttes i fængsles", 30);
        squareList[31] = new StreetSquare("Amagertorv", new int[]{26, 130, 390, 900, 1100, 1275}, 300, 6, 31, 2, propertyController);
        squareList[32] = new StreetSquare("Vimmelskaftet", new int[]{26, 130, 390, 900, 1100, 1275}, 300, 6, 32, 2, propertyController);
        squareList[33] = new ChanceSquare("Prøv lykken", 33, cardController);
        squareList[34] = new StreetSquare("Nygade", new int[]{28, 150, 450, 1000, 1200, 1400}, 320, 6, 34, 2, propertyController);
        squareList[35] = new Transport("D/S Bornholm 1866", new int []{25, 50, 100, 200},200,8,35,3,propertyController);
        squareList[36] = new ChanceSquare("Prøv lykken", 36, cardController);
        squareList[37] = new StreetSquare("Frederiksberggade", new int[]{35, 175, 500, 1100, 1300, 1500}, 350, 7, 37, 1, propertyController);
        squareList[38] = new LuxuryTax("Statsskat", 38);
        squareList[39] = new StreetSquare("Rådhuspladsen", new int[]{50, 200, 600, 1400, 1700, 2000}, 400, 7, 39, 1, propertyController);

    }
}