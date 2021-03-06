package dk.sdu.mmmi.swe.gtg.common.data;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    public final Signal<EntityPartPair> onPartAdded;
    public final Signal<EntityPartPair> onPartRemoved;
    private final UUID ID;
    private final Set<IFamily> families;
    private final Map<Class, IEntityPart> parts;

    public Entity() {
        ID = UUID.randomUUID();
        parts = new ConcurrentHashMap<>();
        families = new HashSet<>();

        onPartAdded = new Signal<>();
        onPartRemoved = new Signal<>();
    }

    public void addPart(IEntityPart part) {
        parts.put(part.getClass(), part);
        onPartAdded.fire(
                new EntityPartPair(this, part)
        );
    }

    public void removePart(Class partClass) {
        onPartRemoved.fire(
                new EntityPartPair(this, parts.remove(partClass))
        );
    }

    public <E extends IEntityPart> E getPart(Class<E> partClass) {
        return (E) parts.get(partClass);
    }

    public Collection<? extends IEntityPart> getParts() {
        return parts.values();
    }

    public boolean hasPart(Class<? extends IEntityPart> partClass) {
        return parts.containsKey(partClass);
    }

    public String getID() {
        return ID.toString();
    }

    public void addFamily(IFamily family) {
        this.families.add(family);
    }

    public void removeFromFamily(IFamily family) {
        this.families.remove(family);
    }

    public boolean isMember(IFamily family) {
        return families.contains(family);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (Objects.isNull(object) || getClass() != object.getClass()) {
            return false;
        }

        Entity entity = (Entity) object;
        return entity.getID().equals(getID());
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
