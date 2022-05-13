package dk.sdu.mmmi.swe.gtg.screens.commonscreen;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;

public interface ScreenManagerSPI {
    void changeScreen(String screen);

    void setGameInputProcessor();

    GameData getGameData();

    void addOnScreenChangeListener(ISignalListener<String> listener);
}
