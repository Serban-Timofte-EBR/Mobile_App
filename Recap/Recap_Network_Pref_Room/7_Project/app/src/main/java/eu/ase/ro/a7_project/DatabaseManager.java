package eu.ase.ro.a7_project;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Project.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_projects_class")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract ProjectDao getProjectDao();
}
