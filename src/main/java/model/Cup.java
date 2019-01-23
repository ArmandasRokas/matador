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
    private int currTurn = 0-1;
    private int rollScoreList[] = {1, 2, 0, 30, 33, 33, 3, 0, 2, 3, 15, 10, 28, 6, 0, 29, 39, 7, 32, 10, 19
            //, 22
    };
    private int doubleRolls[] = {0, 1, 2, 5, 8,

            10, 12, 15, 17};

    public void roll() {  currTurn++;  }

    public int getCurrentRollScore() {
        if(currTurn >= rollScoreList.length) {
            return 0;  //Just to test if we're out of bound on our rollScoreList
        }
        int rollScore =  rollScoreList[currTurn];

        return rollScore;
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
