package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.EntityPartPair;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntitySystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.*;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Engine implements IEngine {
    private final ISignalListener<EntityPartPair> onPartRemoved;
    private final ISignalListener<EntityPartPair> onPartAdded;
    private final List<IEntitySystem> systemsToBeStarted;

    @Reference
    private IPluginManager pluginManager;

    @Reference
    private ISystemManager systemManager;

    @Reference
    private IEntityManager entityManager;

    @Reference
    private IFamilyManager familyManager;

    private boolean shouldReset;

    public Engine() {
        systemsToBeStarted = new CopyOnWriteArrayList<>();

        onPartRemoved = onPartAdded = (signal, entityPartPair) -> {
            familyManager.updateFamilyMembership(
                    entityPartPair.getEntity()
            );
        };

        shouldReset = false;
    }

    @Override
    public void update(GameData gameData) {
        pluginManager.update(gameData);

        for (IEntitySystem system : systemsToBeStarted) {
            system.addedToEngine();
        }
        systemsToBeStarted.clear();

        systemManager.update(gameData);

        if (shouldReset) {
            this.resetInternal(gameData);
        }
    }

    @Override
    public void addEntity(Entity entity) {
        entity.onPartAdded.add(onPartAdded);
        entity.onPartRemoved.add(onPartRemoved);

        entityManager.addEntity(entity);

        familyManager.updateFamilyMembership(entity);
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

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    @Override
    public void addEntityProcessingService(IProcessingSystem service) {
        this.systemManager.addEntityProcessingService(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removeEntityProcessingService(IProcessingSystem service) {
        this.systemManager.removeEntityProcessingService(service);
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    @Override
    public void addPostEntityProcessingService(IPostProcessingSystem service) {
        this.systemManager.addPostEntityProcessingService(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removePostEntityProcessingService(IPostProcessingSystem service) {
        this.systemManager.removePostEntityProcessingService(service);
    }

    @Override
    public void addEntityListener(IFamily family, IEntityListener listener) {
        familyManager.addEntityListener(family, listener);
    }

    @Override
    public void addEntityListener(IFamily family, IEntityListener listener, boolean iterate) {
        getEntitiesFor(family).forEach(listener::onEntityAdded);

        familyManager.addEntityListener(family, listener);
    }

    @Override
    public void removeEntityListener(IFamily family, IEntityListener listener) {
        familyManager.removeEntityListener(family, listener);
    }

    private void resetInternal(GameData gameData) {
        shouldReset = false;

        this.pluginManager.uninstallAll(gameData);
        this.entityManager.reset();
        this.familyManager.reset();
        this.systemManager.reset();
        this.pluginManager.installAll(gameData);
        //this.pluginManager.reset(gameData);
    }

    @Override
    public void reset() {
        shouldReset = true;
    }
}
