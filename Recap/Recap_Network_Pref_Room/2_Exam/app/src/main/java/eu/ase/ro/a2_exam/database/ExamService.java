package eu.ase.ro.a2_exam.database;

import android.content.Context;
import android.telecom.Call;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.a2_exam.model.Exam;
import eu.ase.ro.a2_exam.network.AsyncTaskRunner;
import eu.ase.ro.a2_exam.network.Callback;

public class ExamService {
    private final ExamDao examDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public ExamService(Context context) {
        examDao = DatabaseManager.getInstance(context).getExpenseDao();
    }

    public void getAll(Callback<List<Exam>> callback) {
        Callable<List<Exam>> callable = examDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Exam exam, Callback<Exam> callback) {
        Callable<Exam> callable = new Callable<Exam>() {
            @Override
            public Exam call() throws Exception {
                if (exam.getId() > 0) {
                    return null;
                }

                long id = examDao.insert(exam);

                if (id < 0) {
                    exam.setId(id);
                    return exam;
                }

                return null;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Exam exam,  Callback<Exam> callback) {
        Callable<Exam> callable = new Callable<Exam>() {
            @Override
            public Exam call() throws Exception {
                if (exam.getId() <= 0) {
                    return null;
                }

                int result = examDao.update(exam);

                if (result <= 0) {
                    return null;
                }

                return exam;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Exam exam, Callback<Boolean> callback) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (exam.getId() <= 0) {
                    return null;
                }

                int count = examDao.delete(exam);

                return count == 1;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
