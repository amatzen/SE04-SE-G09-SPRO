package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class ExpirationPart {

    private float expiration;

    private float countdown;

    public ExpirationPart(float expiration) {
        this.expiration = expiration;
        this.countdown = expiration;
    }

    public void update(float delta) {
        countdown -= delta;
    }

    public boolean isExpired() {
        return countdown <= 0;
    }

    public float getExpiration() {
        return expiration;
    }

    public float getCountdown() {
        return countdown;
    }

}
