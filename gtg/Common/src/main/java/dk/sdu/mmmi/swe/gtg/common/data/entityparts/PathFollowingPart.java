package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class PathFollowingPart implements IEntityPart {

    private float lookAhead;

    public PathFollowingPart(float lookAhead) {
        this.lookAhead = lookAhead;
    }

    public PathFollowingPart() {
        this(5);
    }

    public float getLookAhead() {
        return lookAhead;
    }

    public void setLookAhead(float lookAhead) {
        this.lookAhead = lookAhead;
    }

    @Override
    public void destroy() {

    }
}
