package dk.sdu.mmmi.swe.gtg.renderingsystem.internal;

import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RenderingSystem implements IEntityProcessingService {

    private List<? extends Entity> entities;

    private OrthographicCamera cam;

    public void addedToEngine(IEngine engine) {
        System.out.println("hewwo");
        entities = engine.getEntitiesFor(
                Family.builder().with(TransformPart.class, TexturePart.class).get()
        );
    }

    public void process(GameData gameData) {
        List<? extends Entity> entities = new ArrayList<>(this.entities);

        entities.sort((e1, e2) -> {
            TransformPart t1 = e1.getPart(TransformPart.class);
            TransformPart t2 = e2.getPart(TransformPart.class);
            return (int) Math.signum(t2.getPosition().z - t1.getPosition().z);
        });


    }
}
