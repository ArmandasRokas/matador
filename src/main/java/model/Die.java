package model;

import java.util.Random;

public class Die {
    private int eyes;
    private int minEyes, maxEyes;

    /**
     * Constructor of the Die
     */
    public Die(int minEyes, int maxEyes) {
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