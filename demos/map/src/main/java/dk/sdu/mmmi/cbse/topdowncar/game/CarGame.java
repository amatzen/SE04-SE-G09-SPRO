package dk.sdu.mmmi.cbse.topdowncar.game;

import com.badlogic.gdx.Game;
import dk.sdu.mmmi.cbse.topdowncar.game.screens.PlayScreen;

public class CarGame extends Game {
    @Override
    public void create() {
        setScreen(new PlayScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
