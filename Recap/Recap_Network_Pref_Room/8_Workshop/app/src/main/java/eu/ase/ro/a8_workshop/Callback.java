package eu.ase.ro.a8_workshop;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
