package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignal;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

public class ATMBalancePart implements IEntityPart {
    private int balance = 0;
    private boolean robbed = false;

    public final ISignal<ATMBalancePart> onRobbed;

    public ATMBalancePart(int balance) {
        this.balance = balance;
        this.onRobbed = new Signal<>();
    }

    public ATMBalancePart() {
        this((int) Math.floor(Math.random() * 250000));
    }

    public int getBalance() {
        return balance;
    }

    public boolean isRobbed() {
        return robbed;
    }

    public void setRobbed(boolean robbed) {
        if (robbed) {
            this.balance = 0;
            onRobbed.fire(this);
        }
        this.robbed = robbed;
    }

    @Override
    public void destroy() {
        onRobbed.dispose();
    }
}
