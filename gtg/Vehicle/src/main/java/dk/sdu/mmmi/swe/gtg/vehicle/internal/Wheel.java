package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

public class Wheel extends Entity {

    private boolean powered;
    private boolean turnDirection;
    private float maxTurningAngle;

    public Wheel(boolean powered, float maxTurningAngle, boolean turnDirection) {
        this.powered = powered;
        this.maxTurningAngle = maxTurningAngle;
        this.turnDirection = turnDirection;
    }

    public Wheel(boolean powered) {
        this(powered, 0, false);
    }

    public float getMaxAngle() {
        return maxTurningAngle;
    }

    public boolean getTurnDirection() {
        return turnDirection;
    }

    public boolean isPowered() {
        return powered;
    }
}
