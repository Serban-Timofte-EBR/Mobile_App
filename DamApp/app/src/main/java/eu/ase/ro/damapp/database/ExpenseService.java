package eu.ase.ro.damapp.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.damapp.model.Expense;
import eu.ase.ro.damapp.network.AsyncTaskRunner;
import eu.ase.ro.damapp.network.Callback;

public class ExpenseService {

    private final ExpenseDao expenseDao;
    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public ExpenseService(Context context) {
        expenseDao = DatabaseManager.getInstance(context).getExpenseDao();
    }

    public void getAll(Callback<List<Expense>> callback) {
        Callable<List<Expense>> callable = expenseDao::getAll;
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Expense expense, Callback<Expense> callback) {
        Callable<Expense> callable = () -> {
            if (expense.getId() > 0) {
                return null;
            }
            long id = expenseDao.insert(expense);
            if (id < 0) {
                return null;
            }
            expense.setId(id);

            return expense;
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void update(Expense expense, Callback<Expense> callback) {
        Callable<Expense> callable = () -> {
            if (expense.getId() <= 0) {
                return null;
            }
            int count = expenseDao.update(expense);

            return count == 1 ? expense : null;
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Expense expense, Callback<Boolean> callback) {
        Callable<Boolean> callable = () -> {
            if (expense.getId() <= 0) {
                return false;
            }

            int count = expenseDao.delete(expense);

            return count == 1;
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
