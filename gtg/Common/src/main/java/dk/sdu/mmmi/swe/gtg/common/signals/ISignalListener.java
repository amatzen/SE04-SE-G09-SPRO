package dk.sdu.mmmi.swe.gtg.common.signals;

@FunctionalInterface
public interface ISignalListener<T> {

    void onSignal(Signal<T> signal, T value);

}
