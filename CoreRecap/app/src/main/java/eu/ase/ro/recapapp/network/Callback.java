package eu.ase.ro.recapapp.network;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
