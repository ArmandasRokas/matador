package model;

import java.util.Random;

//FixMe Gennemlæs kommentarene
/**@author
 * @version
 *
 * Defines the Die class and all of its fields and methods
 * Class to represent a single die to be used throughout the gameController
 */
public class Die {
    private int eyes;               //Eyes of the dice
    private int minEyes, maxEyes;   //Range of eyes on the die

    /**
     * Constructor of the Die
     */
    public Die(int minEyes, int maxEyes) {
        this.eyes = minEyes;
        this.minEyes = minEyes;
        this.maxEyes = maxEyes;
    }

    /**
     * Sets the eyes on the die through a pseudorandom method with the use of the class "Random"
     */
    public void rollDie() {
        Random r = new Random();

        int randomNum = r.nextInt(maxEyes - minEyes + 1);
        this.eyes = randomNum + minEyes;
    }

    /**
     * Get method for Eyes
     *
     * @return Number of current eyes on the die
     */
    public int getEyes() {
        return eyes;
    }
}