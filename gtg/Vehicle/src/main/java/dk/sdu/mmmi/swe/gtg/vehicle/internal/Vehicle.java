package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

import java.util.ArrayList;
import java.util.List;

public class Vehicle extends Entity {

    private List<Wheel> allWheels;
    private List<Wheel> revolvingWheels;

    public Vehicle() {
        this.allWheels = new ArrayList<>(4);
        this.revolvingWheels = new ArrayList<>(2);
    }

    public List<Wheel> getAllWheels() {
        return allWheels;
    }

    public List<Wheel> getRevolvingWheels() {
        return revolvingWheels;
    }
}
