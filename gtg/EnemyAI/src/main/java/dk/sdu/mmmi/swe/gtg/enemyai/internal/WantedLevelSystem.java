package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ATMBalancePart;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import dk.sdu.mmmi.swe.gtg.enemyai.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;

@Component
public class WantedLevelSystem implements IPlugin, IWantedLevelSystem {

    private ISignalListener<ATMBalancePart> listener;

    private IFamily atmFamily;

    private IEntityListener atmListener;

    @Override
    public void install(IEngine engine, GameData gameData) {

    }

    @Override
    public void registerCrime(float weight) {

    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {

    }
}
