package dk.sdu.mmmi.swe.gtg.atm.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.atm.ATM;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class ATMPlugin implements IPlugin, IProcessingSystem {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    @Reference
    private CollisionSPI collisionSPI;

    @Reference
    private MapSPI mapSPI;

    @Reference
    private IEngine engine;

    private ATM atm;

    private ICollisionListener collisionListener;

    private TexturePart atmTexturePart;


    public ATMPlugin() {
    }

    @Override
    public void install(GameData gameData) {
        IFamily familyA = Family.builder().forEntities(ATM.class).get();
        IFamily familyB = Family.builder()
            .forEntities(Vehicle.class)
            .with(PlayerPart.class)
            .get();

        collisionListener = new CollisionListener() {
            @Override
            public IFamily getFamilyA() {
                return familyA;
            }

            @Override
            public IFamily getFamilyB() {
                return familyB;
            }

            @Override
            public void beginContact(CollisionEntity atm, CollisionEntity entityB) {
                if (!atm.isSensorPart()) {
                    return;
                }

                atm.getEntity().getPart(ProximityPart.class).setProximity(true);
                atm.getEntity().getPart(ATMTimerPart.class).startTimer();
            }

            @Override
            public void endContact(CollisionEntity atm, CollisionEntity entityB) {
                if (!atm.isSensorPart()) {
                    return;
                }

                atm.getEntity().getPart(ProximityPart.class).setProximity(false);
                atm.getEntity().getPart(ATMTimerPart.class).stopTimer();
            }
        };
        collisionSPI.addListener(collisionListener);
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

    private TexturePart getBodyTexture() {
        if (this.atmTexturePart == null) {
            this.atmTexturePart = getTexture("assets/atm.png");
        }
        return this.atmTexturePart;
    }

    @Override
    public void uninstall(GameData gameData) {
        engine.removeEntity(atm);
        collisionSPI.removeListener(collisionListener);
    }

    @Override
    public void addedToEngine() {
        List<Vector2> coordinates = mapSPI.getATMPositions();

        Vector2 atmSize = new Vector2(1, 1.5f);

        float sensorRadius = 10;

        for (Vector2 coordinate : coordinates) {

            Vector2 atmposition = new Vector2(coordinate);

            BodyPart atmBody = new BodyPart(shapeFactory.createRectangle(
                    atmposition, atmSize, BodyDef.BodyType.StaticBody,
                    1,
                    false));

            SensorPart sensorPart = new SensorPart(shapeFactory.createCircle(
                    atmposition, sensorRadius, BodyDef.BodyType.StaticBody,
                    1,
                    true));

            this.atm = new ATM();

            atmBody.getBody().setUserData(this.atm);
            sensorPart.getBody().setUserData(this.atm);

            this.atm.addPart(atmBody);
            this.atm.addPart(sensorPart);

            this.atm.addPart(new ProximityPart());
            this.atm.addPart(new ATMBalancePart());
            this.atm.addPart(new ATMTimerPart());

            TransformPart transformPart = new TransformPart();
            transformPart.setScale(1f / 184f, 1.5f / 423f);

            this.atm.addPart(transformPart);
            this.atm.addPart(getBodyTexture());

            engine.addEntity(this.atm);
        }
    }

    @Override
    public void process(GameData gameData) {

    }
}
