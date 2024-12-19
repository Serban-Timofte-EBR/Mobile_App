package eu.ase.ro.a9_jurnal;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

public class JurnalService {
    private final JurnalDao jurnalDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public JurnalService(Context context) {
        this.jurnalDao = DatabaseManager.getInstance(context).getJurnalDao();
    }

    public void getAll(Callback<List<Jurnal>> callback) {
        Callable<List<Jurnal>> callable = jurnalDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Jurnal jurnal, Callback<Jurnal> callback) {
        Callable<Jurnal> callable = new Callable<Jurnal>() {
            @Override
            public Jurnal call() throws Exception {
                if (jurnal.getId() > 0) {
                    return null;
                }

                long id = jurnalDao.insert(jurnal);

                if (id < 0) {
                    return null;
                }

                jurnal.setId(id);
                return jurnal;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Jurnal jurnal, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (jurnal.getId() < 0) {
                    return null;
                }

                int count = jurnalDao.delete(jurnal);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
