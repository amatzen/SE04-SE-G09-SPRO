package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.ISystemManager;
import org.osgi.service.component.annotations.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SystemManager implements ISystemManager {
    private List<IEntityProcessingService> entityProcessors = new CopyOnWriteArrayList<>();
    private List<IPostEntityProcessingService> entityPostProcessors = new CopyOnWriteArrayList<>();

    public SystemManager() {

    }

    @Override
    public void addEntityProcessingService(IEntityProcessingService service) {
        this.entityProcessors.add(service);
    }

    @Override
    public void removeEntityProcessingService(IEntityProcessingService service) {
        this.entityProcessors.remove(service);
    }

    @Override
    public void addPostEntityProcessingService(IPostEntityProcessingService service) {
        this.entityPostProcessors.add(service);
    }

    @Override
    public void removePostEntityProcessingService(IPostEntityProcessingService service) {
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

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessors;
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return entityPostProcessors;
    }
}
