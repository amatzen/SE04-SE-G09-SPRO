package dk.sdu.mmmi.cbse.topdowncar.game.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static dk.sdu.mmmi.cbse.topdowncar.game.Constants.MAP_NAME;

public class MapLoader implements Disposable {


    private static final String MAP_WALL = "walls";
    private static final String MAP_PLAYER = "player";
    private static final float OBJECT_DENSITY = 1f;
    private static final float PLAYER_DENSITY = 0.4f;


    private final World mWorld;
    private final TiledMap mMap;

    /**
     * Main MapLoader constructor
     * @param world {@link com.topdowncar.game.screens.PlayScreen#mWorld} used to control and add physics objects
     */
    public MapLoader(World world) {
        this.mWorld = world;

        FileHandleResolver resolver = new ClasspathFileHandleResolver();
        TmxMapLoader loader = new TmxMapLoader(resolver);
        mMap = loader.load(MAP_NAME);

        final Array<RectangleMapObject> walls = mMap.getLayers().get(MAP_WALL).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<RectangleMapObject>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            ShapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2).scl(1/21f), // position
                    new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2).scl(1/21f), // size
                    BodyDef.BodyType.StaticBody, mWorld, OBJECT_DENSITY, false);
        }
    }

    /**
     * Return player main rectangle that is used in
     * {@link com.topdowncar.game.entities.Car#Car(float, float, float, MapLoader, int, World)}
     * to position the player correctly
     * @return player rectangle received from map
     */
    public Body getPlayer() {
        final Rectangle rectangle = mMap.getLayers().get(MAP_PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        return ShapeFactory.createRectangle(
                new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2).scl(1/21f), // position
                new Vector2(rectangle.getWidth() / 2, rectangle.getHeight() / 2).scl(1/21f), // size
                BodyDef.BodyType.DynamicBody, mWorld, PLAYER_DENSITY, false);
    }


    @Override
    public void dispose() {
        mMap.dispose();
    }
}
