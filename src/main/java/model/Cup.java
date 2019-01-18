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

    /**
     * Constructor of Cup
     */
    public Cup(){
        this.d1 = new Die(1,1);
        this.d2 = new Die(1,1);
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
        return 1;
    }


    public int getEyesDie1() { return d1.getEyes(); }
    public int getEyesDie2() {
        return d2.getEyes();
    }

    public boolean getIfSameFacevaluePrevious2Turns() {
        boolean[] extraTurnList = new boolean[2];

        for(int i = 0; i < extraTurnList.length; i++){
            if(getEyesDie1() == getEyesDie2()){
                extraTurnList[i] = true;
            } else{
                extraTurnList[i] = false;
                break;
            }
        }

        if(extraTurnList[0] && extraTurnList[1]){
            return true;
        }else{
            return false;
        }
    }
}
