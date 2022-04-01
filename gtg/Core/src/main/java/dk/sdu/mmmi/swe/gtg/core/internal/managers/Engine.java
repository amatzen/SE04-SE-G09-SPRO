package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntitySystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IFamilyManager;
import dk.sdu.mmmi.swe.gtg.common.services.managers.ISystemManager;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Engine implements IEngine {

    private ISystemManager systemManager;
    private IEntityManager entityManager;
    private IFamilyManager familyManager;

    private final ISignalListener<Entity> onPartRemoved;
    private final ISignalListener<Entity> onPartAdded;

    private final List<IEntitySystem> systemsToBeStarted;

    public Engine() {
        systemsToBeStarted = new CopyOnWriteArrayList<>();

        onPartRemoved = onPartAdded = (signal, entity) -> {
            familyManager.updateFamilyMembership(entity);
        };
    }

    @Override
    public void update(GameData gameData) {
        for (IEntitySystem system : systemsToBeStarted) {
            system.addedToEngine(this);
        }
        systemsToBeStarted.clear();

        systemManager.update(gameData);
    }

    @Override
    public String addEntity(Entity entity) {
        entity.onPartAdded.add(onPartAdded);
        entity.onPartRemoved.add(onPartRemoved);

        final String id = entityManager.addEntity(entity);

        familyManager.updateFamilyMembership(entity);

        return id;
    }

    @Override
    public void removeEntity(String entityID) {
        Entity entity = entityManager.getEntity(entityID);
        removeEntity(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        entityManager.removeEntity(entity);

        familyManager.updateFamilyMembership(entity, true);

        entity.onPartRemoved.remove(onPartAdded);
        entity.onPartRemoved.remove(onPartAdded);
    }

    @Override
    public Entity getEntity(String ID) {
        return entityManager.getEntity(ID);
    }

    @Override
    public List<? extends Entity> getEntitiesFor(IFamily family) {
        return familyManager.getEntitiesFor(family);
    }

    @Override
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        return entityManager.getEntities(entityTypes);
    }

    public void setEntityManager(IEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void removeEntityManager(IEntityManager entityManager) {
        this.entityManager = null;
    }

    public void setSystemManager(ISystemManager systemManager) {
        this.systemManager = systemManager;
    }

    public void removeSystemManager(ISystemManager systemManager) {
        this.systemManager = null;
    }

    public void setFamilyManager(IFamilyManager familyManager) {
        this.familyManager = familyManager;
    }

    public void removeFamilyManager(IFamilyManager familyManager) {
        this.familyManager = null;
    }

    @Override
    public void addEntityProcessingService(IEntityProcessingService service) {
        this.systemManager.addEntityProcessingService(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removeEntityProcessingService(IEntityProcessingService service) {
        this.systemManager.removeEntityProcessingService(service);
    }

    @Override
    public void addPostEntityProcessingService(IPostEntityProcessingService service) {
        this.systemManager.addPostEntityProcessingService(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removePostEntityProcessingService(IPostEntityProcessingService service) {
        this.systemManager.removePostEntityProcessingService(service);
    }
}
