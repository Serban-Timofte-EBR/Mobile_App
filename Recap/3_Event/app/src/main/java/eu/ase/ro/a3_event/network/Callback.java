package eu.ase.ro.a3_event.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
