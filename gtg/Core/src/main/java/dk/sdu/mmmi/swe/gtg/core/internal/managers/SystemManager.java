package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.ISystemManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SystemManager implements ISystemManager {
    private final List<IProcessingSystem> entityProcessors = new CopyOnWriteArrayList<>();
    private final List<IPostProcessingSystem> entityPostProcessors = new CopyOnWriteArrayList<>();

    @Override
    public void addEntityProcessingService(IProcessingSystem service) {
        this.entityProcessors.add(service);
    }

    @Override
    public void removeEntityProcessingService(IProcessingSystem service) {
        this.entityProcessors.remove(service);
    }

    @Override
    public void addPostEntityProcessingService(IPostProcessingSystem service) {
        this.entityPostProcessors.add(service);
    }

    @Override
    public void removePostEntityProcessingService(IPostProcessingSystem service) {
        this.entityPostProcessors.remove(service);
    }

    @Override
    public void update(GameData gameData) {
        getEntityProcessingServices().forEach(entityProcessor -> {
            entityProcessor.process(gameData);
        });

        getPostEntityProcessingServices().forEach(entityPostProcessor -> {
            entityPostProcessor.process(gameData);
        });
    }

    private Collection<? extends IProcessingSystem> getEntityProcessingServices() {
        return entityProcessors;
    }

    private Collection<? extends IPostProcessingSystem> getPostEntityProcessingServices() {
        return entityPostProcessors;
    }
}
