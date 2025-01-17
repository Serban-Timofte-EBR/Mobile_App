package eu.ase.ro.damapp.network;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(Callable<R> asyncOperation,
                                 Callback<R> mainThreadOperation){

        executor.execute(runAsyncOperation(asyncOperation, mainThreadOperation));

    }

    private <R> @NonNull Runnable runAsyncOperation(Callable<R> asyncOperation, Callback<R> mainThreadOperation) {
        return () -> {
            //suntem pe un thread secundar, paralel cu cel de UI
            try {
                R result = asyncOperation.call();

                // ce se executa in .post este rulat in UI Thread
                handler.post(() -> mainThreadOperation
                        .runResultOnUIThread(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
