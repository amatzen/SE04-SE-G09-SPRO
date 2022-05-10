package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class ProximityPart implements IEntityPart {
    private boolean inProximity = false;

    public void setProximity(boolean s) {
        this.inProximity = s;
    }

    public boolean isInProximity() {
        return inProximity;
    }

    @Override
    public void destroy() {

    }
}
