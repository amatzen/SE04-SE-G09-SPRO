package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

import java.util.Objects;
import java.util.Set;

public class Family implements IFamily {

    private Set<Class<? extends Entity>> entities;

    private Set<Class<? extends IEntityPart>> parts;

    private Set<Class<? extends IEntityPart>> excludedParts;

    public Family() {
    }

    public static IFamilyBuilder builder() {
        return new FamilyBuilder();
    }

    public Set<Class<? extends Entity>> getEntities() {
        return entities;
    }

    protected void setEntities(Set<Class<? extends Entity>> entities) {
        this.entities = entities;
    }

    public Set<Class<? extends IEntityPart>> getParts() {
        return parts;
    }

    public Set<Class<? extends IEntityPart>> getExcludedParts() {
        return excludedParts;
    }

    protected void setParts(Set<Class<? extends IEntityPart>> parts) {
        this.parts = parts;
    }

    protected void setExcludedParts(Set<Class<? extends IEntityPart>> excludedParts) {
        this.excludedParts = excludedParts;
    }

    public boolean matches(Entity entity) {
        if (this.excludedParts != null) {
            for (Class<? extends IEntityPart> parts : this.excludedParts) {
                if (entity.hasPart(parts)) {
                    return false;
                }
            }
        }

        if (this.entities != null) {
            if (!this.entities.contains(entity.getClass())) {
                return false;
            }
        }

        if (this.parts != null) {
            for (Class<? extends IEntityPart> part : this.parts) {
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
        if (Objects.isNull(object) || getClass() != object.getClass()) return false;
        Family family = (Family) object;
        return Objects.equals(entities, family.entities)
                && Objects.equals(parts, family.parts)
                && Objects.equals(excludedParts, family.excludedParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entities, parts, excludedParts);
    }
}
