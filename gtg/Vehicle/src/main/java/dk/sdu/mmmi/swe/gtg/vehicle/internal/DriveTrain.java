package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public class DriveTrain implements IEntityPart {

    private Wheel[] wheels;

    public DriveTrain(Wheel ... wheels) {
        this.wheels = wheels;
    }

    @Override
    public void destroy() {
        for (Wheel wheel : wheels) {
            wheel.getBody().getWorld().destroyBody(wheel.getBody());
        }
    }
}
