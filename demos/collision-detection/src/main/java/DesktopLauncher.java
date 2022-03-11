import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 60;
        config.title = "Collision Detection Demo";
        new LwjglApplication(new CollisionDetectionDemo(), config);
    }
}
