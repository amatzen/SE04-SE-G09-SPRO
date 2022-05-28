package dk.sdu.mmmi.swe.gtg.worldmanager.internal;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class WorldManager implements IWorldManager, IProcessingSystem, IPlugin {

    private final float timeStep = 1 / 60f;
    private Vector2 gravity;
    private World world;
    private float accumulator = 0f;
    private List<? extends Entity> entities;

    private boolean debugRendering = false;

    @Reference
    private IEngine engine;

    public WorldManager() {
    }

    public Body createBody(BodyDef def) {
        return world.createBody(def);
    }

    public Joint createJoint(JointDef def) {
        return world.createJoint(def);
    }

    @Override
    public void render(Box2DDebugRenderer renderer, Matrix4 projectionMatrix) {
        /*
         * Remove comment for debugging of entities
         */

        if (debugRendering) {
            renderer.render(world, projectionMatrix);
        }
    }

    @Override
    public void setContactLister(ContactListener contactLister) {
        this.world.setContactListener(contactLister);
    }

    @Override
    public void addedToEngine() {
        entities = engine.getEntitiesFor(
                Family.builder().with(BodyPart.class, TransformPart.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        /*
         * Using a constant step time apparently performs better.
         * https://stackoverflow.com/questions/20848442/libgdx-speeding-up-a-whole-game-using-box2d
         */
        float frameTime = Math.min(gameData.getDelta(), 0.25f);
        accumulator += frameTime;
        if (accumulator >= timeStep) {
            world.step(timeStep, 6, 2);
            accumulator -= timeStep;
        }

        entities.forEach(entity -> {
            BodyPart bodyPart = entity.getPart(BodyPart.class);
            TransformPart transformPart = entity.getPart(TransformPart.class);

            Vector2 bodyPosition = bodyPart.getBody().getPosition();
            transformPart.setPosition(bodyPosition);

            transformPart.setRotation(bodyPart.getBody().getAngle());
        });

        if (gameData.getKeys().isPressed(GameKeys.Q)) {
            this.debugRendering = !this.debugRendering;
        }
    }

    @Override
    public void install(GameData gameData) {
        gravity = new Vector2(0, 0);
        world = new World(gravity, true);
    }

    @Override
    public void uninstall(GameData gameData) {
        //world.dispose();
    }
}
