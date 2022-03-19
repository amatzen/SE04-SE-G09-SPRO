package dk.sdu.mmmi.swe.gtg.common.signals;

public interface ISignalListener<T> {

    void onSignal(Signal<T> signal, T value);

}
