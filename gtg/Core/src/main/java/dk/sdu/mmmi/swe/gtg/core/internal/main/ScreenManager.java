package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Gdx;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignal;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import org.osgi.service.component.annotations.Component;

@Component
public class ScreenManager implements ScreenManagerSPI {
    public final ISignal<String> onScreenChange = new Signal<>();

    private GameData gameData = new GameData();

    public ScreenManager() {
    }

    @Override
    public void changeScreen(String screenName) {
        onScreenChange.fire(screenName);
    }

    @Override
    public void setGameInputProcessor() {
        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }

    @Override
    public void addOnScreenChangeListener(ISignalListener<String> listener) {
        onScreenChange.add(listener);
    }
}
