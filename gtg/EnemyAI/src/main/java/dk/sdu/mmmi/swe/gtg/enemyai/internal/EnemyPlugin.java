package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class EnemyPlugin implements IPlugin {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Override
    public void install(IEngine engine, GameData gameData) {
        engine.addEntity(createEnemy());
    }

    private Entity createEnemy() {
        Entity enemy = new Enemy();

        Vector2 position = new Vector2(134.28f, 84);

        Body enemyBody = shapeFactory.createCircle(
                position,
                1,
                BodyDef.BodyType.DynamicBody,
                260f,
                false
        );
        enemyBody.setUserData(enemy);

        enemyBody.setLinearDamping(0.15f);

        enemy.addPart(new BodyPart(enemyBody));

        TransformPart transformPart = new TransformPart();
        transformPart.setScale(1f / 56f, 1f / 56f);
        enemy.addPart(transformPart);

        enemy.addPart(new SeekingPart());
        enemy.addPart(new PathFollowingPart(8f));
        enemy.addPart(new LifePart());

        enemy.addPart(getTexture("assets/police.png"));

        return enemy;
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.getEntitiesFor(
            Family.builder().forEntities(Enemy.class).get()
        ).forEach(engine::removeEntity);
    }
}
