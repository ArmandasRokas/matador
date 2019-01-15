package ui;

import controller.PlayerController;
import gui_fields.*;
import gui_main.GUI;
import model.GameBoard;
import model.Player;
import model.square.Square;

import java.awt.Color;

public class GUIBoundary {
    private GUI gui = new GUI();
    protected GUI_Player[] playerList;
    private GUI_Field[] fieldList = gui.getFields();

    public void setupGUIFields(int index, String name) {
            fieldList[index].setTitle(name);
            fieldList[index].setDescription(name);

            if(index == 2 || index == 7 || index == 17 || index == 22 || index == 33 || index == 36) {
                fieldList[index].setSubText("");
            }else if(index == 10){
                fieldList[index].setSubText("På besøg");
            }else if(index == 30){
                fieldList[index].setSubText("Til Fængsel");
            }

    }
//    public void setupGUIFields(int index, String name, int cash) {
//            fieldList[index].setTitle(name);
//            fieldList[index].setDescription(name);
//            fieldList[index].setSubText(Integer.toString(cash));
//    }


//    public void updateGUIFields() {
//
//    }

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

    public int takeTurn(PlayerController plCtrl) {
        //FIXME show which player has a turn.
        String message = "Det er " + plCtrl.getCurrPlayerName() + "s tur. ";
        String res = gui.getUserButtonPressed(message + "Tryk på [Kast terninger] for at kaste terningerne","Kast terninger");
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
}
