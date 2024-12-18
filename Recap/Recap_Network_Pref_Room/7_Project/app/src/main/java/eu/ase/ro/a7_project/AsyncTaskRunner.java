package eu.ase.ro.a7_project;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Incepe executia pe un thread secundar
    // Prin callable mi se intoarce rezultatul de pe threadul secundarAs
    // Practic pentru citire de la endpoint, in callable avem HttpManager.call() care imi returneaza stringul de la npoint
    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        executor.execute(() -> {
            try {
                R result = callable.call();

                handler.post(() -> callback.runResultOnUIThread(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
