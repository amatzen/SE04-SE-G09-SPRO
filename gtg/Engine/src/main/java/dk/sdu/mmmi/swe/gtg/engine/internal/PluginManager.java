package dk.sdu.mmmi.swe.gtg.engine.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IPluginManager;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PluginManager implements IPluginManager {

    private final List<IPlugin> entityPlugins = new CopyOnWriteArrayList<>();
    private final List<IPlugin> pluginsToBeInstalled = new CopyOnWriteArrayList<>();
    private final List<IPlugin> pluginsToBeUninstalled = new CopyOnWriteArrayList<>();

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addPlugin(IPlugin plugin) {
        this.entityPlugins.add(plugin);
        this.pluginsToBeInstalled.add(plugin);
    }

    public void removePlugin(IPlugin plugin) {
        this.entityPlugins.remove(plugin);
        this.pluginsToBeUninstalled.add(plugin);
    }

    @Override
    public void update(GameData gameData) {
        pluginsToBeInstalled.forEach(plugin -> plugin.install(gameData));
        pluginsToBeInstalled.clear();

        pluginsToBeUninstalled.forEach(plugin -> plugin.uninstall(gameData));
        pluginsToBeUninstalled.clear();
    }

    @Override
    public void uninstallAll(GameData gameData) {
        for (IPlugin plugin : entityPlugins) {
            plugin.uninstall(gameData);
        }
    }

    @Override
    public void installAll(GameData gameData) {
        for (IPlugin plugin : entityPlugins) {
            plugin.install(gameData);
        }
    }

}
