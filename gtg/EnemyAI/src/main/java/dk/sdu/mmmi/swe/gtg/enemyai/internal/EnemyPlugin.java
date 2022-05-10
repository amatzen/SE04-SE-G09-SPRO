package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import org.osgi.service.component.annotations.Component;

@Component
public class EnemyPlugin implements IPlugin {
    @Override
    public void install(IEngine engine, GameData gameData) {
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {

    }
}
