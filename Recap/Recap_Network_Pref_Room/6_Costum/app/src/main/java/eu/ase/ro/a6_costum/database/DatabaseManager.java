package eu.ase.ro.a6_costum.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.ase.ro.a6_costum.model.Costume;

@Database(entities = {Costume.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_costumes_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract CostumeDao getCostumeDao();
}
