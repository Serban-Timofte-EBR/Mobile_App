package eu.ase.ro.a4_pacient.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Handler handler = new Handler(Looper.getMainLooper());

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        executorService.execute(() -> {
            try {
                R result = callable.call();

                handler.post(() -> callback.runOnUIThread(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
