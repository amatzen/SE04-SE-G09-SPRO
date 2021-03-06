package dk.sdu.mmmi.swe.gtg.renderingsystem.internal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.sort.QuickSort;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

@Component
public class RenderingSystem implements IPostProcessingSystem {

    private List<? extends Entity> entities;

    @Reference
    private IEngine engine;

    public void addedToEngine() {
        entities = engine.getEntitiesFor(
                Family.builder().with(TransformPart.class, TexturePart.class).get()
        );
    }

    public void process(GameData gameData) {
        final SpriteBatch batch = gameData.getSpriteBatch();

        final List<? extends Entity> entities = new ArrayList<>(this.entities);

        QuickSort.sort(entities, (e1, e2) -> {
            TransformPart t1 = e1.getPart(TransformPart.class);
            TransformPart t2 = e2.getPart(TransformPart.class);
            return (int) Math.signum(t1.getZ() - t2.getZ());
        });

        batch.setProjectionMatrix(gameData.getCamera().combined);
        batch.enableBlending();
        batch.begin();

        for (Entity entity : entities) {

            TransformPart transformPart = entity.getPart(TransformPart.class);
            TexturePart texturePart = entity.getPart(TexturePart.class);

            if (texturePart.getTexture() == null) {
                continue;
            }


            final float width = texturePart.getTexture().getRegionWidth();
            final float height = texturePart.getTexture().getRegionHeight();

            final float originX = transformPart.getOrigin().x + width * 0.5f;
            final float originY = transformPart.getOrigin().y + height * 0.5f;

            batch.draw(
                    texturePart.getTexture(),
                    transformPart.getPosition().x - width * 0.5f,
                    transformPart.getPosition().y - height * 0.5f,
                    originX,
                    originY,
                    width,
                    height,
                    transformPart.getScale().x,
                    transformPart.getScale().y,
                    transformPart.getRotation() / ((float) Math.PI * 2f) * 360
            );
        }

        batch.end();
    }
}
