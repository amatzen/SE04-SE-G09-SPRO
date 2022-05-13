package dk.sdu.mmmi.swe.gtg.atm.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMRobbedTimer;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMTimerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ProximityPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class ATMControlSystem implements IProcessingSystem {
    private List<? extends Entity> atmEntities;

    @Reference
    private ICrimeAction crimeAction;

    @Reference
    private IWantedLevelSystem wantedLevelSystem;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
        this.atmEntities = engine.getEntitiesFor(
            Family.builder().with(ATMBalancePart.class).get()
        );

        this.atmEntities.forEach(entity -> {
            ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
            timerPart.setAction(5.00,() -> {
                crimeAction.commit(entity);
                wantedLevelSystem.reportCrime(10f);
            });

            ATMRobbedTimer robbedTimer = entity.getPart(ATMRobbedTimer.class);
            robbedTimer.startTimer();
            robbedTimer.setAction(5.00, () -> {
                System.out.println("Should heal now");
                entity.addPart(crimeAction.getAtmTexture());
                entity.getPart(ATMBalancePart.class).generateBalance();
                entity.getPart(ATMBalancePart.class).setRobbed(false);
                robbedTimer.resetTimer();
                timerPart.resetTimer();
            });
        });

    }

    @Override
    public void process(GameData gameData) {

        this.atmEntities.stream()
            .filter(atmEntity -> atmEntity.getPart(ProximityPart.class).isInProximity())
            .forEach(entity -> {
                ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
                timerPart.update(gameData.getDelta());
            });
        this.atmEntities.stream()
                .filter(entity -> entity.getPart(ATMBalancePart.class).isRobbed())
                .forEach(entity -> {
                    ATMRobbedTimer robbedTimer = entity.getPart(ATMRobbedTimer.class);
                    robbedTimer.update(gameData.getDelta());
                });
    }




}
