package eu.ase.ro.a9_jurnal;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}