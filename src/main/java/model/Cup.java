package model;

//TODO java doc
/**@author Hold 44
 * @version 08/11-2018
 *
 * Defines the Cup class and its field and methods
 * Class to keep the dices that the player has to roll with to move
 */
public class Cup {
    private Die d1, d2; //Instances of dices
    int testCup = 0;    //TODO SLET FØR AFLEVERING
    int testDie = 0;    //TODO SLET FØR AFLEVERING

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
     * Add the eyes of the two dices
     *
     * @return  Sum of the two dices
     */ //TODO ÆNDRE TILBAGE HER FØR AFLEVERING!
//    public int getCurrentRollScore() {
//        return getEyesDie1() + getEyesDie2();
//    }

//    public int getEyesDie1() {
//        return d1.getEyes();
//    }
//    public int getEyesDie2() {
//        return d2.getEyes();
//    }

    public int getCurrentRollScore() {
//        return 36;
        int[] array = new int[]{30, 1, 1 , 1, 1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        return array[testCup++];
    }

    public int getEyesDie1() {
        return 1;
    }
    public int getEyesDie2() {
//        return 2;
        int[] array = new int[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,2, 2, 2, 2, 2, 2, 2, 2, 2,2,2,2,2,2,2,2,2,2,2};
        return array[testDie++];
    }
}
