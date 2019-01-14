package model;


import controller.ChanceCardController;
import controller.PropertyController;
import model.square.Square;
import model.square.property.StreetSquare;

public class GameBoard {


    private Square[] squareList;

    public GameBoard(PropertyController propertyController, ChanceCardController cardController){

        squareList = new Square[40];
        setBoard(propertyController, cardController);



    }

    public Square[] getSquareList(){
        return squareList;
    }

    private void setBoard(PropertyController propertyController, ChanceCardController cardController) {

        //    ChanceSquare CSReference = new ChanceSquare("Chance", gameController.getPlayers(), this);

        //TODO fix prices
        //    squareList [0] = new StartSquare("Start");
        squareList [1] = new StreetSquare("Rødovrevej", new int[]{10000, 250, 750, 2250, 4000, 6000}, 1200, 0, 1, 2,propertyController);
        //     squareList [2] = new ChanceSquare("Prøv lykken", 0, gui_boundary);
        squareList [2] = new StreetSquare("Hvidovrevej", new int []{5000, 250, 750, 2250, 4000, 6000}, 1200, 0, 3, 2, propertyController);
        //      squareList [3] = new PropertySquare("Hvidovrevej", new int []{50, 250, 750, 2250, 4000, 6000}, 1200, 0, 3, gui_boundary);
        //      squareList [4] = new Tax("Indkomstskat", 4000, 10, gui_boundary);
        //      squareList [5] = new Fleet("Helsingør/Helsingborg",  new int []{500, 1000, 2000, 4000},4000, gui_boundary);
//        squareList [6] = new PropertySquare("Roskildevej", new int []{100, 600, 1800, 5400, 8000, 11000}, 2000, 1, 6, propertyController);
//        //      squareList [7] = new ChanceSquare("Prøv lykken", 1, gui_boundary);
//        squareList [8] = new PropertySquare("Valby Langgade", new int []{100, 600, 1800, 5400, 8000, 11000}, 2000, 1, 8, propertyController);
//        squareList [9] = new PropertySquare("Allégade", new int []{150, 800, 2000, 6000, 9000, 12000}, 2400, 1, 9, propertyController);
//        //       squareList [10] = new Parking("'I fængslet'", gui_boundary);
//        squareList [11] = new PropertySquare("Frederiksberg Allé", new int []{200, 1000, 3000, 9000, 12500, 15000}, 2800, 2, 11, propertyController);
//        //      squareList [12] = new Brewery("Tuborg Squash", 3000, gui_boundary);
//        squareList [13] = new PropertySquare("Bülowsvej", new int []{200, 1000, 3000, 9000, 12500, 15000}, 2800, 2, 13, propertyController);
//        squareList [14] = new PropertySquare("Gl. Kongevej", new int []{250, 1250, 3750, 10000, 14000, 18000}, 3200, 2, 14, propertyController);
//        //      squareList [15] = new Fleet("Mols-linien", new int []{500, 1000, 2000, 4000},4000, gui_boundary);
//        squareList [16] = new PropertySquare("Bernstoffsvej", new int []{300, 1400, 4000, 11000, 15000, 19000}, 3600, 3, 16, propertyController);
//        //       squareList [17] = new ChanceSquare("Prøv lykken", 2, gui_boundary);
//        squareList [18] = new PropertySquare("Hellerupvej", new int []{300, 1400, 4000, 11000, 15000, 19000}, 3600, 3, 18, propertyController);
//        squareList [19] = new PropertySquare("Strandvejen", new int []{350, 1600, 4400, 12000, 16000, 20000}, 4000, 3, 19, propertyController);
//        //      squareList [20] = new Parking("'Parkering'", gui_boundary);
//        squareList [21] = new PropertySquare("Trianglen", new int []{350, 1800, 5000, 14000, 17500, 21000}, 4400, 4, 21, propertyController);
//        //      squareList [22] = new ChanceSquare("Prøv lykken", 3, gui_boundary);
//        squareList [23] = new PropertySquare("Østerbrogade", new int []{350, 1800, 5000, 14000, 17500, 21000}, 4400, 4, 23, propertyController);
//        squareList [24] = new PropertySquare("Grønningen", new int []{400, 2000, 6000, 15000, 18500, 22000}, 4800, 4, 24, propertyController);
//        //      squareList [25] = new Fleet("Gedser/Rostock", new int []{500, 1000, 2000, 4000},4000, gui_boundary);
//        squareList [26] = new PropertySquare("Bredgade", new int []{450, 2200, 6600, 16000, 19500, 23000}, 5200, 5, 26, propertyController);
//        squareList [27] = new PropertySquare("Kgs. Nytorv", new int []{450, 2200, 6600, 16000, 19500, 23000}, 5200, 5, 27, propertyController);
//        //      squareList [28] = new Brewery("Coca Cola", 3000, gui_boundary);
//        squareList [29] = new PropertySquare("Østergade", new int []{500, 2400, 7200, 17000, 20500, 24000}, 5600, 5, 29, propertyController);
//        //      squareList [30] = new Jail("De fængsles", gui_boundary);
//        squareList [31] = new PropertySquare("Amagertorv", new int []{550, 2600, 7800, 18000, 22000, 25000}, 6000, 6, 31, propertyController);
//        squareList [32] = new PropertySquare("Vimmelskaftet", new int []{550, 2600, 7800, 18000, 22000, 25000}, 6000, 6, 32, propertyController);
//        //      squareList [33] = new ChanceSquare("Prøv lykken", 4, gui_boundary);
//        squareList [34] = new PropertySquare("Nygade", new int []{600, 3000, 9000, 20000, 24000, 28000}, 6400, 6, 34, propertyController);
//        //       squareList [35] = new Fleet("Rødby/Puttgarten", new int []{500, 1000, 2000, 4000},4000, gui_boundary);
//        //       squareList [36] = new ChanceSquare("Prøv lykken", 5, gui_boundary);
//        squareList [37] = new PropertySquare("Frederiksberggade", new int []{700, 3500, 10000, 22000, 26000 ,30000}, 7000, 7, 37, propertyController);
//        //     squareList [38] = new Tax("Statsskat", 2000, 0, gui_boundary);
//        squareList [39] = new PropertySquare("Rådhuspladsen", new int[]{1000, 4000, 12000, 28000, 34000, 40000}, 8000, 7, 39, propertyController);
//
//        setPropertySquareSiblings();
    }






}
