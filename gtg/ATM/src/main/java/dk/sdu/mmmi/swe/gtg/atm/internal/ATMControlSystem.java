package dk.sdu.mmmi.swe.gtg.atm.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMTimerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ProximityPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class ATMControlSystem implements IProcessingSystem {
    private List<? extends Entity> atmEntities;

    @Reference
    private ICrimeAction crimeAction;

    @Override
    public void addedToEngine(IEngine engine) {
        this.atmEntities = engine.getEntitiesFor(
            Family.builder().with(ATMBalancePart.class).get()
        );

        this.atmEntities.forEach(entity -> {
            ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
            timerPart.setAction(5.00,() -> {
                crimeAction.commit(entity);
            });
        });
    }

    @Override
    public void process(GameData gameData) {
        this.atmEntities.stream()
            .filter(entity -> entity.getPart(ProximityPart.class).isInProximity())
            .forEach(entity -> {
                ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
                timerPart.update(gameData.getDelta());
            });
    }
}
