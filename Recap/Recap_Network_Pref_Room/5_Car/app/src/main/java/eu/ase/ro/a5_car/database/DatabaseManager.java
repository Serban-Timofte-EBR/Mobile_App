package eu.ase.ro.a5_car.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.ase.ro.a5_car.model.Car;

@Database(entities = {Car.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_cars_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract CarDao getCarDao();
}
