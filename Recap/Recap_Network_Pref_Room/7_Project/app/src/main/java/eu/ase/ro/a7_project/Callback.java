package eu.ase.ro.a7_project;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
