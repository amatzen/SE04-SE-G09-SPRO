package dk.sdu.mmmi.swe.gtg.common.services.plugin;


import dk.sdu.mmmi.swe.gtg.common.data.GameData;

public interface IPlugin {
    /**
     * Start
     * <p>
     * Called when the plugin is first created.
     *
     * @param gameData Game data.
     * @pre The application has been started.
     * @post The entity plugin has been initialized.
     */
    void install(GameData gameData);

    /**
     * Stop
     * <p>
     * Called when the application is stopped.
     *
     * @param gameData Game data.
     * @pre The plugin has been started.
     * @post The entity plugin has been taken down.
     */
    void uninstall(GameData gameData);
}
