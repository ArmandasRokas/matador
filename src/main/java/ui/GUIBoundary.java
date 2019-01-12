package ui;

import gui_fields.*;
import gui_main.GUI;
import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();
    protected GUI_Player[] playerList;
    private GUI_Field[] fieldList = gui.getFields();

    public int askForPlayerCount(int minPlayers, int maxPlayers) {
        int playerCount = gui.getUserInteger("Vælg antal spillere (3-6)", minPlayers, maxPlayers);
        playerList = new GUI_Player[playerCount];
        return playerCount;
    }

    public void setUpPlayer(int playerID, String name, int balance, Color color) {
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

    public int takeTurn() {
        //FIXME show which player has a turn.
        String res = gui.getUserButtonPressed("Tryk på [Kast terninger] for at kaste terningerne","Kast terninger");
        int switchRes = 0;
        switch (res){
            case "Kast terninger":
                switchRes = 1;
                break;
        }

        return switchRes;
    }

    public void setDices(int eyesDie1, int eyesDie2) {
        gui.setDice(eyesDie1, eyesDie2);
    }

    public void updateBalance(int playerID, int balance) {
        playerList[playerID].setBalance(balance);
    }

    public boolean askToBuyProperty(int playerID, int fieldID){

        fieldList[1].setTitle("Rød");
        boolean answer = gui.getUserLeftButtonPressed(
                playerList[playerID].getName() + ", du har mulighed at købe " +
                        fieldList[fieldID].getTitle() + ". Vil du det?",
                "ja", "nej");

        return answer;
    }

    public void showCurrScenarioForPlayer(String scenario) {

        //TODO switch statment show player action, when player do something else than buy or rent property.
        gui.getUserButtonPressed(scenario, "OK");

    }

    public void setOwnerOnSquare(int playerID, int squareIndex){

        GUI_Ownable ownable = (GUI_Ownable) fieldList[squareIndex];
        ownable.setOwnerName(playerList[playerID].getName());
        ownable.setSubText(playerList[playerID].getName());
        ownable.setBorder(playerList[playerID].getCar().getPrimaryColor());
        ownable.setRentLabel("");
    }
}
