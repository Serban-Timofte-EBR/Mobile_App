package eu.ase.ro.a7_project;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class ProjectService {
    private final ProjectDao projectDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public ProjectService(Context context) {
        this.projectDao = DatabaseManager.getInstance(context).getProjectDao();
    }

    public void getAll(Callback<List<Project>> callback) {
        Callable<List<Project>> callable = projectDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Project project, Callback<Project> callback) {
        Callable<Project> callable = new Callable<Project>() {
            @Override
            public Project call() throws Exception {
                if (project.getId() > 0) {
                    Log.i("NPOINT", "ID Pozitiv");
                    return null;
                }

                long id = projectDao.insert(project);

                if (id < 0) {
                    Log.i("NPOINT", "ID negativ");
                    return null;
                }
                Log.i("NPOINT", "Inseram: " + project.toString());
                project.setId(id);
                return project;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Project project, Callback<Project> callback) {
        Callable<Project> callable = new Callable<Project>() {
            @Override
            public Project call() throws Exception {
                if (project.getId() <= 0) {
                    return null;
                }

                int count = projectDao.update(project);

                if (count < 1) {
                    return null;
                }

                return project;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Project project, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (project.getId() <= 0) {
                    return null;
                }

                int count = projectDao.delete(project);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
