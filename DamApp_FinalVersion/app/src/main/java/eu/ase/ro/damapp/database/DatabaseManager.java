package eu.ase.ro.damapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.damapp.model.DateConverter;
import eu.ase.ro.damapp.model.Expense;

@Database(entities = {Expense.class}, version =  1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_db").fallbackToDestructiveMigration().build();
        return databaseManager;
    }

    public abstract ExpenseDao getExpenseDao();
}
