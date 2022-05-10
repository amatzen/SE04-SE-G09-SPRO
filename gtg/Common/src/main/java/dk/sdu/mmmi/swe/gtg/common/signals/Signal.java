package dk.sdu.mmmi.swe.gtg.common.signals;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Signal<T> implements ISignal<T> {

    private final List<ISignalListener<T>> listeners;

    public Signal() {
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void add(ISignalListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(ISignalListener<T> listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public void fire(T value) {
        for (ISignalListener<T> listener : listeners) {
            listener.onSignal(this, value);
        }
    }

    @Override
    public void dispose() {
        listeners.clear();
    }
}
