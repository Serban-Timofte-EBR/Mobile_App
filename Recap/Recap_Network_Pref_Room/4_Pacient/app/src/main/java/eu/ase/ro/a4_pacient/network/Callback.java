package eu.ase.ro.a4_pacient.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
