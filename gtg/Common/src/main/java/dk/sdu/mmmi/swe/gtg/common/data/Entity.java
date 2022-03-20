package dk.sdu.mmmi.swe.gtg.common.data;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID;

    private Set<IFamily> families;

    private Map<Class, EntityPart> parts;

    public final Signal<Entity> onPartAdded;
    public final Signal<Entity> onPartRemoved;

    public Entity() {
        ID = UUID.randomUUID();
        parts = new ConcurrentHashMap<>();
        families = new HashSet<>();

        onPartAdded = new Signal<>();
        onPartRemoved = new Signal<>();
    }
    
    public void addPart(EntityPart part) {
        parts.put(part.getClass(), part);
        onPartAdded.fire(this);
    }

    public void removePart(Class partClass) {
        parts.remove(partClass);
        onPartRemoved.fire(this);
    }
    
    public <E extends EntityPart> E getPart(Class<E> partClass) {
        return (E) parts.get(partClass);
    }

    public boolean hasPart(Class partClass) {
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

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Entity entity = (Entity) object;
        if (entity.getID().equals(getID())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
