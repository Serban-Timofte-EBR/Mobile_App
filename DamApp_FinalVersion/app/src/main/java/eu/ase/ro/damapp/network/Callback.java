package eu.ase.ro.damapp.network;

public interface Callback <R>{

    void runResultOnUIThread(R result);
}
