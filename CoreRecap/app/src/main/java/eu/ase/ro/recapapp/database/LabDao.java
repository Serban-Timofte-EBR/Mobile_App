package eu.ase.ro.recapapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.recapapp.model.Lab;

@Dao
public interface LabDao {
    @Query("SELECT * FROM labs")
    List<Lab> getAll();

    @Insert
    long insert(Lab lab);

    @Update
    int update(Lab lab);

    @Delete
    int delete(Lab lab);
}
