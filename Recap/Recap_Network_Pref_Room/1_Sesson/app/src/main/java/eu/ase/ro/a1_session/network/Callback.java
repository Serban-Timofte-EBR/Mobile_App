package eu.ase.ro.a1_session.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
