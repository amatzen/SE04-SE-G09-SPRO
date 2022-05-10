package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class ATMBalancePart implements IEntityPart {
    private int balance = 0;
    private boolean robbed = false;

    public ATMBalancePart(int balance) {
        this.balance = balance;
    }

    public ATMBalancePart() {
        this.balance = (int) Math.floor(Math.random() * 250000);
    }

    public int getBalance() {
        return balance;
    }

    public boolean isRobbed() {
        return robbed;
    }

    public void setRobbed(boolean robbed) {
        this.robbed = robbed;
    }

    @Override
    public void destroy() {
    }
}
