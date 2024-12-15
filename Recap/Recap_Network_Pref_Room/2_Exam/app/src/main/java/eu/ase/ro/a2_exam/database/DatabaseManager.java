package eu.ase.ro.a2_exam.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.a2_exam.model.DateConvertor;
import eu.ase.ro.a2_exam.model.Exam;

@Database(entities = {Exam.class}, version = 1, exportSchema = false)
@TypeConverters(value = {DateConvertor.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_exam_db")
                .fallbackToDestructiveMigration()
                .build();

        return databaseManager;
    }

    public abstract ExamDao getExpenseDao();
}
