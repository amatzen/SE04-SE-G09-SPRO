package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import java.util.LinkedList;
import java.util.List;

public class ATMTimerPart implements IEntityPart {
    private double timer = 0.00;
    private boolean isActive = false;

    private List<ExpiryAction> actions = new LinkedList<>();

    public void startTimer() {
        this.timer = 0.00;
        this.isActive = true;
    }

    public void stopTimer() {
        this.isActive = false;
    }

    public double getTimer() {
        return timer;
    }

    public void resetTimer() {
        this.timer = 0.00;
        for (ExpiryAction a : this.actions) {
            a.hasRan = false;
        }
    }

    public void setTimer(double timer) {
        this.timer = timer;
    }

    public void update(float delta) {
        System.out.println(this.isActive);
        if (this.isActive) {
            this.timer += delta;
            this.actions.stream()
                .filter(a -> !a.hasRan)
                .forEach(a -> a.run(this.timer));
        }
    }

    public void addAction(double expiry, Runnable action) {
        this.actions.add(new ExpiryAction(expiry, action));
    }

    @Override
    public void destroy() { }

    private class ExpiryAction {
        public Runnable runnable;
        public double expiry;
        public boolean hasRan = false;

        public ExpiryAction(double expiry, Runnable runnable) {
            this.runnable = runnable;
            this.expiry = expiry;
        }

        public ExpiryAction(Runnable runnable) {
            this.runnable = runnable;
            this.expiry = 3.00;
        }

        public void run(double timer) {
            if (expiry <= timer) {
                this.hasRan = true;
                runnable.run();
            }
        }
    }
}
