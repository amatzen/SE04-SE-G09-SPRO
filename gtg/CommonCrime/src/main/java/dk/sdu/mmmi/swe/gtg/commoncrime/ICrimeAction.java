package dk.sdu.mmmi.swe.gtg.commoncrime;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;

public interface ICrimeAction {
    public void commit(Entity entity);
    public TexturePart getRobbedAtmTexture();
    public TexturePart getAtmTexture();

}
