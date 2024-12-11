package eu.ase.ro.recapapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import eu.ase.ro.recapapp.model.DateConverter;
import eu.ase.ro.recapapp.model.Lab;

// Mark as database !!
@Database(entities = {Lab.class}, version = 1, exportSchema = false) // Go to model/Lab to mark as entity
@TypeConverters(DateConverter.class)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "core_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract LabDao getLabDao();
}
