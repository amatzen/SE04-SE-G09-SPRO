package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntitySystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.ISystemManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SystemManager implements ISystemManager {

    private final List<IProcessingSystem> entityProcessors;
    private final List<IPostProcessingSystem> entityPostProcessors;

    private final List<IEntitySystem> systemsToBeStarted;


    public SystemManager() {
        systemsToBeStarted = new CopyOnWriteArrayList<>();
        entityProcessors = new CopyOnWriteArrayList<>();
        entityPostProcessors = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addProcessingSystem(IProcessingSystem service) {
        this.entityProcessors.add(service);
    }

    @Override
    public void removeProcessingSystem(IProcessingSystem service) {
        this.entityProcessors.remove(service);
    }

    @Override
    public void addPostProcessingSystem(IPostProcessingSystem service) {
        this.entityPostProcessors.add(service);
    }

    @Override
    public void removePostProcessingSystem(IPostProcessingSystem service) {
        this.entityPostProcessors.remove(service);
    }

    @Override
    public void update(GameData gameData) {
        for (IEntitySystem system : systemsToBeStarted) {
            system.addedToEngine();
        }
        systemsToBeStarted.clear();

        getEntityProcessingServices().forEach(entityProcessor -> {
            entityProcessor.process(gameData);
        });

        getPostEntityProcessingServices().forEach(entityPostProcessor -> {
            entityPostProcessor.process(gameData);
        });
    }

    @Override
    public void reset() {
        for (IEntitySystem system : entityProcessors) {
            system.addedToEngine();
        }

        for (IEntitySystem system : entityPostProcessors) {
            system.addedToEngine();
        }
    }

    private Collection<? extends IProcessingSystem> getEntityProcessingServices() {
        return entityProcessors;
    }

    private Collection<? extends IPostProcessingSystem> getPostEntityProcessingServices() {
        return entityPostProcessors;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    @Override
    public void addEntityProcessingService(IProcessingSystem service) {
        this.addProcessingSystem(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removeEntityProcessingService(IProcessingSystem service) {
        this.removeProcessingSystem(service);
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    @Override
    public void addPostEntityProcessingService(IPostProcessingSystem service) {
        this.addPostProcessingSystem(service);
        this.systemsToBeStarted.add(service);
    }

    @Override
    public void removePostEntityProcessingService(IPostProcessingSystem service) {
        this.removePostProcessingSystem(service);
    }
}
