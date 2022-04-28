package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public interface IFamilyBuilder {

    IFamilyBuilder forEntities(Class<? extends Entity>... entity);

    IFamilyBuilder with(Class<? extends IEntityPart>... parts);

    IFamily get();

}
