package eu.ase.ro.labex;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
