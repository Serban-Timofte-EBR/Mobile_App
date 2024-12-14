package eu.ase.ro.a1_session.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.a1_session.model.DateConvertor;
import eu.ase.ro.a1_session.model.Session;

@Database(entities = {Session.class}, version = 1, exportSchema = false)
@TypeConverters(DateConvertor.class)
public abstract class DatabaseManager extends RoomDatabase{
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "sessions_db")
                .fallbackToDestructiveMigration()
                .build();

        return databaseManager;
    }

    public abstract SessionDao getSessionDao();
}
