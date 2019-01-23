package model;

public class Cup {
    private Die d1, d2;

    public Cup(){
        this.d1 = new Die(1,6);
        this.d2 = new Die(1,6);
    }

//    public void roll(){
//        d1.rollDie();
//        d2.rollDie();
//    }

//    public int getCurrentRollScore() {
//        return getEyesDie1() + getEyesDie2();
//    }

//    public int getEyesDie1() {
//        return d1.getEyes();
//    }
//    public int getEyesDie2() {
//        return d2.getEyes();
//    }


    //These are made for demonstration, original methods above these
    private int rollScoreList[] = new int[]{1, 2, 1, 30, 33, 33, 3, 2, 3, 15, 10, 29, 39, 6, 28, 8, 3, 10, 19};
    private int doubleRolls[] = new int[]{1, 2, 3, 6,
            8,
            //9,
            10,
            //11,
            12, 15, 17};
    private int currTurn = 0;

    public void roll() {    }

    public int getCurrentRollScore() {
        if(currTurn >= rollScoreList.length) {
            return 0;  //Just to test if we're out of bound on our rollScoreList
        }
        return rollScoreList[currTurn++];
    }

    public int getEyesDie1() {
        return 1;
    }
    public int getEyesDie2() {
        for(int i = 0 ; i < doubleRolls.length ; i++) {
            int x = doubleRolls[i];

            if(currTurn == x) {
                return 1;
            }
        }
        return 2;
    }
}
