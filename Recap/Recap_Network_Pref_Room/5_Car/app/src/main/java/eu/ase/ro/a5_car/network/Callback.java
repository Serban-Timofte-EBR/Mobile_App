package eu.ase.ro.a5_car.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
