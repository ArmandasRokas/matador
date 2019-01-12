package model.square;

import controller.PlayerController;

/**@author Hold 44
 * @version
 *
 * Defines the abstract Square class and all of its fields and methods
 * Class to represent the model.squares on board. Is not to be created instances of, since all model.squares are to be unique classes.
 */
public abstract class Square {
    protected String squareName;  //Name of square, used to describe the scenario of the turn
    protected String currScenarioForPlayer;


    /**
     *  Super-constructor of Square's subclasses
     *
     * @param scenario  Name of the square
     */
    public Square(String scenario){
        this.squareName = scenario;
    }


    // getScenario()
    /**
     * Abstract method, to act on the model.squares scenario
     *
     * @param p Instance of player who have landed on the square
     */
    public abstract void landedOn(PlayerController p);

    /**
     * toString method of Square
     *
     * @return  Message of Square
     */
    public String toString(){
        return currScenarioForPlayer;
    }

    public String getSquareName(){
        return squareName;
    }

    public void setCurrScenarioForPlayer(String currScenarioForPlayer){
        this.currScenarioForPlayer = currScenarioForPlayer;
    }
}
