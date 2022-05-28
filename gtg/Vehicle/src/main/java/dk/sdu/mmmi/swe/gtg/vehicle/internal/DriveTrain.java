package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public class DriveTrain implements IEntityPart {

    private final Wheel[] wheels;
    private float turnAngle;

    public DriveTrain(Wheel... wheels) {
        this.wheels = wheels;
        this.turnAngle = 0;
    }

    public Wheel[] getWheels() {
        return wheels;
    }

    public float getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(float turnAngle) {
        this.turnAngle = turnAngle;
    }

    @Override
    public void destroy() {
    }
}
