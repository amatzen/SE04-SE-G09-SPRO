package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.family.IFamily;

public class BoidSeparationPart implements IEntityPart {

    private final IFamily boidFamily;
    private final float criticalDistance;

    public BoidSeparationPart(IFamily boidFamily, float criticalDistance) {
        this.boidFamily = boidFamily;
        this.criticalDistance = criticalDistance;
    }

    public BoidSeparationPart(IFamily boidFamily) {
        this(boidFamily, 4f);
    }

    public IFamily getBoidFamily() {
        return boidFamily;
    }

    public float getCriticalDistance() {
        return criticalDistance;
    }

    @Override
    public void destroy() {

    }
}
