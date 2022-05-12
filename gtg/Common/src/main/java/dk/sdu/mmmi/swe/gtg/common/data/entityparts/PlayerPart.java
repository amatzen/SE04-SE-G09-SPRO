package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class PlayerPart implements IEntityPart {
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    @Override
    public void destroy() {

    }
}
