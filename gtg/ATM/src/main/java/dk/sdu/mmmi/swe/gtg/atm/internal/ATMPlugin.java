package dk.sdu.mmmi.swe.gtg.atm.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.atm.ATM;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SensorPart;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ATMPlugin implements IGamePluginService {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private ATM atm;

    @Override
    public void start(IEngine engine, GameData gameData) {

        Vector2 atmPosition = new Vector2(0, 0);
        Vector2 atmSize = new Vector2(1, 1);
        Vector2 sensorPosition = new Vector2(0, 0);
        float sensorRadius = 5;

        BodyPart atm = new BodyPart(shapeFactory.createRectangle(
                atmPosition, atmSize, BodyDef.BodyType.StaticBody,
                1,
                false));

        SensorPart sensorPart = new SensorPart(shapeFactory.createCircle(
                sensorPosition, sensorRadius, BodyDef.BodyType.StaticBody,
                1,
                true));

        this.atm = new ATM();

        this.atm.addPart(atm);
        this.atm.addPart(sensorPart);

        engine.addEntity(this.atm);

        worldManager.setContactLister(new ContactListener());

        sensorPart.getBody().setUserData(this.atm);

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.removeEntity(atm);
    }

}
