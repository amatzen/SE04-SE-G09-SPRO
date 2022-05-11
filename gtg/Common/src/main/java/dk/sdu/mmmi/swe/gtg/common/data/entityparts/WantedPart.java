package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

public class WantedPart implements IEntityPart {

    private int wantedLevel;

    public final Signal<WantedPart> wantedLevelUpdated;

    public WantedPart() {
        wantedLevelUpdated = new Signal<>();
    }

    public WantedPart(int wantedLevel) {
        this();
        this.wantedLevel = wantedLevel;
    }

    public int getWantedLevel() {
        return wantedLevel;
    }

    public void setWantedLevel(int wantedLevel) {
        this.wantedLevel = wantedLevel;
        wantedLevelUpdated.fire(this);
    }

    @Override
    public void destroy() {
        wantedLevelUpdated.dispose();
    }
}
