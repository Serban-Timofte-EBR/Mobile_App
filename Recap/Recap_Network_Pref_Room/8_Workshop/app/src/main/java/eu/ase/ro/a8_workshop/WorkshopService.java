package eu.ase.ro.a8_workshop;

import android.content.Context;
import android.telecom.Call;

import java.util.List;
import java.util.concurrent.Callable;

public class WorkshopService {
    private final WorkshopDao workshopDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public WorkshopService(Context context) {
        this.workshopDao = DatabaseManager.getInstance(context).getWorkshopDao();
    }

    public void getAll(Callback<List<Workshop>> callback) {
        Callable<List<Workshop>> callable = workshopDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Workshop workshop, Callback<Workshop> callback) {
        Callable<Workshop> callable = new Callable<Workshop>() {
            @Override
            public Workshop call() throws Exception {
                if (workshop.getId() > 0) {
                    return null;
                }

                long id = workshopDao.insert(workshop);
                if (id < 0) {
                    return  null;
                }

                workshop.setId(id);
                return workshop;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Workshop workshop, Callback<Workshop> callback) {
        Callable<Workshop> callable = new Callable<Workshop>() {
            @Override
            public Workshop call() throws Exception {
                if (workshop.getId() < 0) {
                    return null;
                }

                int count = workshopDao.update(workshop);

                if (count < 1) {
                    return null;
                }

                return workshop;
            }
        };
        asyncTaskRunner.executeAsync(callable,callback);
    }

    public void delete(Workshop workshop, Callback<Boolean> callback) {
        Callable<Boolean>  callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (workshop.getId() < 0) {
                    return null;
                }

                int count = workshopDao.delete(workshop);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
