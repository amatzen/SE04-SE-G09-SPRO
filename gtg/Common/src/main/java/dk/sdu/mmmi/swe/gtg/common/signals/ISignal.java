package dk.sdu.mmmi.swe.gtg.common.signals;

public interface ISignal<T> {

    void add(ISignalListener<T> listener);

    void remove(ISignalListener<T> listener);

    void removeAllListeners();

    void fire(T value);

    void dispose();

}
