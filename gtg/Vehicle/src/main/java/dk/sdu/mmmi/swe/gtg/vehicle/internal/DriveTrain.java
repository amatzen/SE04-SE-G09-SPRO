package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public class DriveTrain implements IEntityPart {

    private Wheel[] wheels;

    public DriveTrain(Wheel ... wheels) {
        this.wheels = wheels;
    }

    public Wheel[] getWheels() {
        return wheels;
    }

    @Override
    public void destroy() {

    }
}
