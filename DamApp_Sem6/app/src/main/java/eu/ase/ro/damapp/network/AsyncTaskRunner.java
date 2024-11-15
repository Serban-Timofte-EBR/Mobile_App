package eu.ase.ro.damapp.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(Callable<R> asyncOperation, Callback<R> mainThreadOperation) {
        executor.execute(() -> {
            try {
                R result = asyncOperation.call();
                handler.post(() -> mainThreadOperation.runResultOnUIThread(result));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
