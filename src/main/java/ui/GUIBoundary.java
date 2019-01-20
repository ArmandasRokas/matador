package ui;

import controller.PlayerController;
import gui_fields.*;
import gui_main.GUI;
import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();
    private GUI_Player[] playerList;
    private GUI_Field[] fieldList = gui.getFields();

    public void setPlayerList(GUI_Player[] playerList){
        this.playerList = playerList;
    }

    //Menus
    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount;
        playerCount = gui.getUserInteger("Vælg antal spillere (3-6)", minPlayers, maxPlayers);
        playerList = new GUI_Player[playerCount];
        return playerCount;
    }

    public String[] askForNames(int playerCount) {
        String[] names = new String[playerCount];
        String name;

        for(int i = 0 ; i < playerCount ; i++) {
            do{
                name = gui.getUserString("Skriv navn for spiller nr. " + (i + 1));
            } while(!controlName(names, name));
            names[i] = name;
        }
        return names;
    }

    public int getUserChoice(String[] buttons, String res) {
        int choice = -1;

        for(int i = 0 ; i < buttons.length ; i++) {
            if(buttons[i].equals(res)) {
                choice = i;
            }
        }
        return choice;
    }

    private String[] mainMenuButtons() {
        return new String[]{"[FIRST OPTION]", "Køb Huse", "Sælg Huse"};
    }

    public int takeTurn(PlayerController plCtrl) {
        String[] buttons = mainMenuButtons();
        buttons[0] = "Kast Terninger";
        String res = gui.getUserButtonPressed("Det er " + plCtrl.getCurrPlayerName() + "s tur. Vælg om du vil kaste terningerne eller administerer dine grunde",
                buttons[0], buttons[1],buttons[2] );
        return getUserChoice(buttons, res);
    }

    public int endTurn(PlayerController plCtrl) {
        String[] buttons = mainMenuButtons();
        buttons[0] = "Afslut Tur";
        String res = gui.getUserButtonPressed("Det er " + plCtrl.getCurrPlayerName() + "s tur. Vælg om du vil afslutte din tur eller administerer dine grunde",
                buttons[0], buttons[1], buttons[2]);
        return getUserChoice(buttons, res);
    }

    public String buyBuildings(int[] possibleStreets) {
        String[] possibleStreetNames = getPossibleSquareNamesForBuilding(possibleStreets);
        String res = gui.getUserSelection("Vælg din ejendom fra dropdown menuen og klik på [OK] for at bygge et hus eller hotel på den.", possibleStreetNames);
        return res;
    }

    public String sellBuildings(int[] possibleStreets) {
        String[] possibleStreetNames = getPossibleSquareNamesForBuilding(possibleStreets);
        String res = gui.getUserSelection("Vælg din ejendom fra dropdown menuen og klik på [OK] for at sælge et hus eller hotel på den.", possibleStreetNames);
        return res;
    }

    private String[] getPossibleSquareNamesForBuilding(int[] possibleStreets) {
        String[] possibleStreetNames = new String[possibleStreets.length+1];

        for(int i = 0 ; i < possibleStreets.length ; i++) {
            possibleStreetNames[i] = fieldList[possibleStreets[i]].getTitle();
        }
        possibleStreetNames[possibleStreetNames.length-1] = "Exit";

        return possibleStreetNames;
    }

    public int getOutOfJail(PlayerController plCtrl){
        String[] buttons = new String[]{"Kast For 2 ens", "Betal 50kr", "Brug Frikort"};
        String message = "Det er " + plCtrl.getCurrPlayerName() + "s " + (plCtrl.getTurnsInJail()+1) + ". tur i fængsel. Vælg hvordan du vil komme ud.";
        String res;

        if(plCtrl.hasGetOutOfJailCard()) {
            res = gui.getUserButtonPressed(message, buttons[0], buttons[1], buttons[2]);
        } else {
            res = gui.getUserButtonPressed(message, buttons[0], buttons[1]);
        }
        return getUserChoice(buttons, res);
    }

    public int incomeTax(PlayerController plCtrl){
        String[] buttons = new String[]{"Betal 10%", "Betal 200kr"};
        String message = plCtrl.getCurrPlayerName() + " er landet på indkomstskat og må betale enten 10% af sine værdier, eller betale 200kr.";
        String res = gui.getUserButtonPressed(message + " Vælg hvordan du vil betale indkomstskat ", buttons[0], buttons[1]);
        return getUserChoice(buttons, res);
    }

    public boolean askToBuyProperty(int playerID, String squareName){
        boolean answer = gui.getUserLeftButtonPressed(playerList[playerID].getName() + ", du har mulighed at købe " + squareName + ". Vil du det?",
                "Ja", "Nej");
        return answer;
    }

    public int endGame() { //TODO Will get implemented "No" button when GUI allows it
        String[] buttons = new String[]{"Ja", "Nej"};
        String res = gui.getUserButtonPressed("Vil De spille igen?", buttons[0]);
        return getUserChoice(buttons, res);
    }

    //General communication (information, no choice)
    public void showCurrScenarioForPlayer(String scenario) {
        gui.getUserButtonPressed(scenario, "OK");
    }
    public void declareWinner(PlayerController playerCtrl) {
        gui.showMessage("Tillykke " + playerCtrl.getCurrPlayerName() + "! Du har vundet");
    }

    public void tellPlayerExtraTurn(int playerID) {
        gui.showMessage("Du har været heldig, " + playerList[playerID].getName() + "! De har slået dobbelt og  får en ekstra tur!");
    }

    public void informPlayerGoingToJail(int playerID){
        gui.showMessage(playerList[playerID].getName() + " er blevet taget i at køre for hurtigt, og bliver sat i fængsel!");
    }

    public void showChanceCard(String cardText) {
        gui.displayChanceCard(cardText);
    }

    //Practical methods
        //Sets and removes
    public void setupGUIFields(int index, String name, int housePrice) {
        fieldList[index].setDescription(name);

        if(fieldList[index] instanceof GUI_Chance || fieldList[index] instanceof GUI_Jail || fieldList[index] instanceof GUI_Refuge) {
            fieldList[index].setSubText(name);
        } else {
            fieldList[index].setTitle(name);
        }

        if(fieldList[index] instanceof GUI_Street) {
            fieldList[index].setDescription("Hus pris: " + housePrice);
        }
    }

    public void setupPlayer(int playerID, String name, int balance, Color color) {
        GUI_Car car = new GUI_Car();
        car.setPrimaryColor(color);
        GUI_Player guiPlayer = new GUI_Player(name, balance, car);
        playerList[playerID] = guiPlayer;
        gui.addPlayer(playerList[playerID]);
        fieldList[0].setCar(playerList[playerID], true);
    }

    public void removePlayerByBankrupt(int position, int playerID) {
        fieldList[position].setCar(playerList[playerID],false);
        playerList[playerID].setName(playerList[playerID].getName() + " (Gået fallit)");
    }

        //Updates

    public void movePlayer(int previousPosition, int newPosition, int playerID) {
        fieldList[previousPosition].setCar(playerList[playerID],false);
        fieldList[newPosition].setCar(playerList[playerID],true);
    }
    public void setDices(int eyesDie1, int eyesDie2) {
        gui.setDice(eyesDie1, eyesDie2);
    }

    public void updateBalance(int playerID, int balance) {
        playerList[playerID].setBalance(balance);
    }

    public void setOwnerOnSquare(int playerID, int squareIndex, int rentPrice){
        GUI_Ownable ownable = (GUI_Ownable) fieldList[squareIndex];
        ownable.setOwnerName(playerList[playerID].getName());
        updateRentPrice(squareIndex, rentPrice);
        ownable.setBorder(playerList[playerID].getCar().getPrimaryColor());
        ownable.setRent(Integer.toString(rentPrice));
    }

    public void setOwnerOnSquare(int squareIndex, int buyPrice) {
        GUI_Ownable ownable = (GUI_Ownable) fieldList[squareIndex];
        ownable.setOwnerName(null);
        updateRentPrice(squareIndex, buyPrice);
        ownable.setBorder(Color.GRAY);
        ownable.setRent(Integer.toString(buyPrice));
    }

    public void setHousing(int squareIndex, int numberOfHouses) {
        GUI_Street street = (GUI_Street)fieldList[squareIndex];

        if(numberOfHouses <= 4) {
            street.setHouses(numberOfHouses);
        } else if(numberOfHouses == 5) {
            street.setHotel(true);
        }
    }

    public void updateRentPrice(int squareIndex, int rentPrice) {
        GUI_Ownable ownable = (GUI_Ownable) fieldList[squareIndex];
        if(ownable instanceof GUI_Brewery) {    //Different subtext if a brewery
            ownable.setSubText("Leje: *" + rentPrice + "*");
        } else {    //General subtext
            ownable.setSubText("Leje: " + rentPrice);
        }
    }

    //Control
    private boolean controlName(String[] names, String name) {
        boolean res = true;

        if(!name.isEmpty()) {
            for(String previousName : names) {
                if(name.equals(previousName)) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }
}