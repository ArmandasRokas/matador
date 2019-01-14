package controller;

import model.Player;
import model.square.property.PropertySquare;
import ui.GUIBoundary;

public class BankruptController {

    private GUIBoundary guiBoundary;

    public BankruptController(GUIBoundary guiBoundary){
        this.guiBoundary = guiBoundary;
    }

    public void handleNegativeBalance(PropertySquare propertySquare, PlayerController playerController){


            // vis muligheder til at sælge huse
            // vis mulighder til at pansætte grund


        // hvis ingen mulighder
            // overføre grunde, penge til kreditor og

            // setCurrentPlayerScenario(" du er gået falit.
            // boolean isBankrupt is true
            // slet bilen fra spillerpladen.


    }

    public void transferPropertyToCreditor(PlayerController playerController, PropertySquare propertySquare, Player owner) {
        if(owner == null) {
            PropertySquare[] currentPlayerProperties = playerController.getCurrPlayerProperties();

            for(PropertySquare square: currentPlayerProperties) {
                square.setOwner(null);
            }
        }

    }

}
