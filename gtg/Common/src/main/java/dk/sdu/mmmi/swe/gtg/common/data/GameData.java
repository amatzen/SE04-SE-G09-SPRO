package dk.sdu.mmmi.swe.gtg.common.data;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private final List<Event> events = new CopyOnWriteArrayList<>();

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }
}
