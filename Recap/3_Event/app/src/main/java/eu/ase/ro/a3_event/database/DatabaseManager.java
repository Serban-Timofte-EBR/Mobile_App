package eu.ase.ro.a3_event.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.a3_event.model.DateConverter;
import eu.ase.ro.a3_event.model.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters(value = DateConverter.class)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_events_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract EventsDao getEventDao();
}
