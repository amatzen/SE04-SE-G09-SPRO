package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.enemyai.services.IEnemyFactory;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class EnemyFactory implements IEnemyFactory {

    @Reference
    private ShapeFactorySPI shapeFactory;

    private TexturePart policeTexture;

    @Override
    public Entity createEnemy(Vector2 position) {
        Entity enemy = new Enemy();

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
        enemy.addPart(new BoidSeparationPart(
                Family.builder().forEntities(Enemy.class).get())
        );

        enemy.addPart(getPoliceTexture());

        return enemy;
    }

    private TexturePart getPoliceTexture() {
        if (policeTexture == null) {
            this.policeTexture = getTexture("assets/entities/police.png");
        }
        return this.policeTexture;
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

}
