package dk.sdu.mmmi.swe.gtg.pathfindings.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SeekingPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

import java.util.List;

public class PathfindingsControlSystem implements IProcessingSystem {

    private List<? extends Entity> entities;

    public void addedToEngine(IEngine engine) {
        this.entities = engine.getEntitiesFor(
                Family.builder().with(SeekingPart.class).get()
        );


    }

    public void process(GameData gameData) {

    }
}
