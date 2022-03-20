package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.EntityPart;

import java.util.Objects;
import java.util.Set;

public class Family implements IFamily{

    private Set<Class<? extends Entity>> entities;

    private Set<Class<? extends EntityPart>> parts;

    public Family() {
    }

    public Set<Class<? extends Entity>> getEntities() {
        return entities;
    }

    protected void setEntities(Set<Class<? extends Entity>> entities) {
        this.entities = entities;
    }

    public Set<Class<? extends EntityPart>> getParts() {
        return parts;
    }

    protected void setParts(Set<Class<? extends EntityPart>> parts) {
        this.parts = parts;
    }

    public boolean matches(Entity entity) {
        if (entities != null) {
            if (!this.entities.contains(entity.getClass())) {
                return false;
            }
        }

        if (parts != null) {
            for (Class<? extends EntityPart> part : parts) {
                if (!entity.hasPart(part)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Family family = (Family) object;
        return Objects.equals(entities, family.entities) && Objects.equals(parts, family.parts);
    }

    public static IFamilyBuilder builder() {
        return new FamilyBuilder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(entities, parts);
    }
}