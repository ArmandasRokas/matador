package model;

//FixMe Gennemlæs kommentarene
/**@author Hold 44
 * @version 08/11-2018
 *
 * Defines the Cup class and its field and methods
 * Class to keep the dices that the player has to roll with to move
 */
public class Cup {
    private Die d1, d2; //Instances of dices
    private int nextCupRoll = 0;
    private int nextDieRoll = 0;

    /**
     * Constructor of Cup
     */
    public Cup(){
        this.d1 = new Die(1,6);
        this.d2 = new Die(1,6);
    }

    /**
     * Rolls the dices
     */
    public void roll(){
        d1.rollDie();
        d2.rollDie();
    }

    /**
     * Addd the eyes of the two dices
     *
     * @return  Sum of the two dices
     */
//    public int getCurrentRollScore() {
//        return d1.getEyes() + d2.getEyes();
//    }

    public int getCurrentRollScore() {
        int currRollScore = getEyesDie1() + getEyesDie2();
//        return 30;
        return nextIntCup();
//        return currRollScore;
    }

    private int nextIntCup() {
        int[] rolls = new int[]{5, 10, 5, 5, 5, 5};
        int i = rolls[nextCupRoll++];
        return i;
    }


    public int getEyesDie1() {
//        return d1.getEyes();
        return 1;
    }
    public int getEyesDie2() {
//        return d2.getEyes();
//        return 1;
        return nextIntDie();
    }

    private int nextIntDie() {
        int[] rolls = new int[]{1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,};
        int i = rolls[nextDieRoll++];
        return i;
    }
}
