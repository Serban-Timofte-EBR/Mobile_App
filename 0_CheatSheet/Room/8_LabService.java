package eu.ase.ro.recapapp.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.recapapp.model.Lab;
import eu.ase.ro.recapapp.network.AsyncTaskRunner;
import eu.ase.ro.recapapp.network.Callback;

public class LabService {
    private final LabDao labDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public LabService(Context context) {
        labDao = DatabaseManager.getInstance(context).getLabDao();
    }

    public void getAll(Callback<List<Lab>> callback) {
        Callable<List<Lab>> callable = labDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Lab lab, Callback<Lab> callback) {
        Callable<Lab> callable = new Callable<Lab>() {
            @Override
            public Lab call() throws Exception {
                if (lab.getId() > 0) {
                    return null;
                }

                long id = labDao.insert(lab);

                if (id < 0) {
                    return null;
                }

                return lab;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Lab lab, Callback<Lab> callback) {
        Callable<Lab> callable = new Callable<Lab>() {
            @Override
            public Lab call() throws Exception {
                if (lab.getId() <= 0) {
                    return null;
                }

                int result = labDao.update(lab);

                if (result <= 0) {
                    return null;
                }

                return lab;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Lab lab, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (lab.getId() <= 0) {
                    return null;
                }

                int count = labDao.delete(lab);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
