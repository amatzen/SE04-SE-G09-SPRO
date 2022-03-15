package dk.sdu.mmmi.swe.gtg.common.services.plugin;


import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.World;

public interface IGamePluginService {
    /** Start
     *
     * Called when the plugin is first created.
     * @param gameData Game data.
     * @param world World data.
     * @pre The application has been started.
     * @post The entity plugin has been initialized.
     */
    void start(GameData gameData, World world);

    /** Stop
     *
     * Called when the application is stopped.
     * @param gameData Game data.
     * @param world World data.
     * @pre The plugin has been started.
     * @post The entity plugin has been taken down.
     */
    void stop(GameData gameData, World world);
}
