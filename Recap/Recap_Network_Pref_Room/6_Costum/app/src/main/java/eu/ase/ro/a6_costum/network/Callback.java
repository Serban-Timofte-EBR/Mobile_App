package eu.ase.ro.a6_costum.network;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
