package model;

public class Cup {
    private Die d1, d2;

    public Cup(){
        this.d1 = new Die(1,6);
        this.d2 = new Die(1,6);
    }

    public void roll(){
        d1.rollDie();
        d2.rollDie();
    }

    public int getCurrentRollScore() {
        return getEyesDie1() + getEyesDie2();
    }

    public int getEyesDie1() {
        return d1.getEyes();
    }
    public int getEyesDie2() {
        return d2.getEyes();
    }
}
