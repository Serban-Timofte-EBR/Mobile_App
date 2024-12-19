package eu.ase.ro.a9_jurnal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Jurnal.class}, version =  1, exportSchema = false)
@TypeConverters(value = {DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "jurnals_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract JurnalDao getJurnalDao();
}
