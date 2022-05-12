package dk.sdu.mmmi.swe.gtg.screens.commonscreen;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;

public interface ScreenManagerSPI {
    void changeScreen(String screen);

    void setGameInputProcessor();

    GameData getGameData();
}
