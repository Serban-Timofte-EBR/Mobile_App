package eu.ase.ro.a6_costum.database;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a6_costum.model.Costume;
import eu.ase.ro.a6_costum.network.AsyncTaskRunner;
import eu.ase.ro.a6_costum.network.Callback;

public class CostumeService {
    private final CostumeDao costumeDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public CostumeService(Context context) {
        this.costumeDao = DatabaseManager.getInstance(context).getCostumeDao();
    }

    public void getAll(Callback<List<Costume>> callback) {
        Callable<List<Costume>> callable = costumeDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Costume costume, Callback<Costume> callback) {
        Callable<Costume> callable = new Callable<Costume>() {
            @Override
            public Costume call() throws Exception {
                if (costume.getId() > 0) {
                    return null;
                }

                long id = costumeDao.insert(costume);
                if (id < 0) {
                    return null;
                }

                costume.setId(id);
                return costume;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Costume costume, Callback<Costume> callback) {
        Callable<Costume> callable = new Callable<Costume>() {
            @Override
            public Costume call() throws Exception {
                if (costume.getId() <= 0) {
                    return null;
                }

                int res = costumeDao.update(costume);

                if (res <= 0) {
                    return null;
                }

                return costume;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Costume costume, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (costume.getId() < 0) {
                    return null;
                }

                int count = costumeDao.delete(costume);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
