package ui;

import controller.PlayerController;
import gui_fields.*;
import gui_main.GUI;
import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();
    protected GUI_Player[] playerList;
    private GUI_Field[] fieldList = gui.getFields();

    public void setupGUIFields(int index, String name) {
        fieldList[index].setDescription(name);
        if(fieldList[index] instanceof GUI_Chance || fieldList[index] instanceof GUI_Jail || fieldList[index] instanceof GUI_Refuge) {
            fieldList[index].setSubText(name);
        } else {
            fieldList[index].setTitle(name);
        }
    }

    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = gui.getUserInteger("Vælg antal spillere (3-6)", minPlayers, maxPlayers);
        playerList = new GUI_Player[playerCount];
        return playerCount;
    }

    public void setupPlayer(int playerID, String name, int balance, Color color) {
        GUI_Car car = new GUI_Car();
        car.setPrimaryColor(color);
        GUI_Player guiPlayer = new GUI_Player(name, balance, car);
        playerList[playerID] = guiPlayer;
        gui.addPlayer(playerList[playerID]);
        fieldList[0].setCar(playerList[playerID], true);
    }

    public String[] askForNames(int playerCount) { //TODO Fix at man ikke kan hedde det samme, da det overwriter den forrige GUI_Player
        String[] names = new String[playerCount];

        for(int i = 0 ; i < playerCount ; i++) {
            names[i] = gui.getUserString("Skriv navn for spiller nr. " + (i + 1));
        }

        return names;
    }

    public void movePlayer(int previousPosition, int newPosition, int playerID) {
        fieldList[previousPosition].setCar(playerList[playerID],false);
        fieldList[newPosition].setCar(playerList[playerID],true);
    }

    public void removePlayer(int position, int playerID) {
        fieldList[position].setCar(playerList[playerID],false);
        playerList[playerID].setName(playerList[playerID].getName() + " (gået fallit)");
    }

    private String[] mainMenuButtons() {
        return new String[]{"[FIRST OPTION]", "Køb Huse", "Sælg Huse", "Pantsæt Grunde"};
    }

    public int takeTurn(PlayerController plCtrl) {
        String[] buttons = mainMenuButtons();
        buttons[0] = "Kast Terninger";
        String res = gui.getUserButtonPressed("Det er " + plCtrl.getCurrPlayerName() + "s tur. Vælg om du vil kaste terningerne eller administerer dine grunde", buttons[0], buttons[1]);
        return getUserChoice(buttons, res);
    }

    public int endTurn(PlayerController plCtrl) {
        String[] buttons = mainMenuButtons();
        buttons[0] = "Afslut Tur";
        String res = gui.getUserButtonPressed("Det er " + plCtrl.getCurrPlayerName() + "s tur. Vælg om du vil afslutte din tur eller administerer dine grunde",buttons[0], buttons[1]);
        return getUserChoice(buttons, res);
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

    public String administrateProperties(int[] possibleStreets) {
        int count = 0;
        for(int possibleStreet : possibleStreets) {
            if(possibleStreet != 0) {
                count++;
            }
        }
        String[] possibleStreetNames = new String[count+1];
        for(int possibleStreet : possibleStreets) {
            if(possibleStreet != 0) {

                for(int i = 0 ; i < possibleStreetNames.length ; i++) {
                    if(possibleStreetNames[i] == null) {
                        possibleStreetNames[i] = fieldList[possibleStreet].getDescription();
                        break;
                    }
                }

            }
        }
        possibleStreetNames[count] = "exit";
        String message = "Vælg din ejendom fra dropdown menuen og klik på [OK] for at bygge et hus på den.";
        String res = gui.getUserSelection(message, possibleStreetNames);
        return res;
    }

    public int getOutOfJail(PlayerController plCtrl){
        String[] buttons = new String[]{"Kast For 2 ens", "Betal 50kr", "Brug Frikort"};
        String message = "Det er " + plCtrl.getCurrPlayerName() + "s " + plCtrl.getTurnsInJail()+1 + ". tur i fængsel. Vælg hvordan du vil komme ud.";
        String res;
        if(plCtrl.hasGetOutOfJailCard()) {
            res = gui.getUserButtonPressed(message, buttons[0], buttons[1], buttons[2]);
        } else {
            res = gui.getUserButtonPressed(message, buttons[0], buttons[1]);
        }
        return getUserChoice(buttons, res);
    }

    public void setDices(int eyesDie1, int eyesDie2) {
        gui.setDice(eyesDie1, eyesDie2);
    }

    public void updateBalance(int playerID, int balance) {
        playerList[playerID].setBalance(balance);
    }

    public boolean askToBuyProperty(int playerID, String squareName){

        boolean answer = gui.getUserLeftButtonPressed(
                playerList[playerID].getName() + ", du har mulighed at købe " +
                        squareName + ". Vil du det?",
                "ja", "nej");

        return answer;
    }

    public void showCurrScenarioForPlayer(String scenario) {

        //TODO switch statment show player action, when player do something else than buy or rent property.
        gui.getUserButtonPressed(scenario, "OK");
    }

    public void setOwnerOnSquare(int playerID, int squareIndex, int rentPrice){

        GUI_Ownable ownable = (GUI_Ownable) fieldList[squareIndex];
        ownable.setOwnerName(playerList[playerID].getName());
        ownable.setSubText("Leje: " + Integer.toString(rentPrice));
        ownable.setBorder(playerList[playerID].getCar().getPrimaryColor());
        ownable.setRent(Integer.toString(rentPrice));
    }

    public void declareWinner(int playerID) {
        gui.showMessage("Tillykke " + playerList[playerID].getName() + "! Du har vundet");
    }

    public String endGame() {
        String input = gui.getUserButtonPressed("Vil du spille igen?", "Ja");
        return input;
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
        GUI_Street street = (GUI_Street)fieldList[squareIndex];
        street.setRent(""+rentPrice);
        street.setSubText("Leje: " + rentPrice);
    }

    public void showChanceCard(String cardText) {
        gui.displayChanceCard(cardText);
    }

    public void tellPlayerExtraTurn(int playerID) {
        gui.showMessage("Flyt dem ikke " + playerList[playerID].getName() + "! De har slået dobbelt og fået en ekstra tur!");
    }
}
