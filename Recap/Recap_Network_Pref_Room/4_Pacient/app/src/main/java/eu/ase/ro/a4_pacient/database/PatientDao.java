package eu.ase.ro.a4_pacient.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a4_pacient.model.Patient;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM patients")
    List<Patient> getAll();

    @Insert
    long insert(Patient patient);

    @Update
    int update(Patient patient);

    @Delete
    int delete(Patient patient);
}
