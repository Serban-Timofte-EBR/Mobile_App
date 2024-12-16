package eu.ase.ro.a4_pacient.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.ase.ro.a4_pacient.model.Patient;

@Database(entities = {Patient.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        }

        databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "dam_patients_db")
                .fallbackToDestructiveMigration()
                .build();
        return databaseManager;
    }

    public abstract PatientDao getPatientDao();
}
