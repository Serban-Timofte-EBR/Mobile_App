package eu.ase.ro.a2_exam.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
