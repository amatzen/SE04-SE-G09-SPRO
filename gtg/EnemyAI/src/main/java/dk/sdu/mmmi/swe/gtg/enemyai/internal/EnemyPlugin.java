package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SteeringPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
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
        Entity enemy = new Enemy();

        Vector2 position = new Vector2(134.28f, 84);

        Body enemyBody = shapeFactory.createCircle(
                position,
                1,
                BodyDef.BodyType.DynamicBody,
                260f,
                false
        );

        enemyBody.setLinearDamping(0.15f);

        enemy.addPart(new BodyPart(enemyBody));

        enemy.addPart(new TransformPart());

        enemy.addPart(new SteeringPart());

        engine.addEntity(enemy);
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.getEntitiesFor(
            Family.builder().forEntities(Enemy.class).get()
        ).forEach(engine::removeEntity);
    }
}
