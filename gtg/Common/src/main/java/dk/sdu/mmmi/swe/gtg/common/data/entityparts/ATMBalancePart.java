package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class ATMBalancePart implements IEntityPart {
    private int balance = 0;

    public ATMBalancePart(int balance) {
        this.balance = balance;
    }

    public ATMBalancePart() {
        this.balance = (int) Math.floor(Math.random() * 250000);
    }

    @Override
    public void destroy() {
    }
}
