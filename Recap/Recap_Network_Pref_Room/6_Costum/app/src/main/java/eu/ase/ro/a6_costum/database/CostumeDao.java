package eu.ase.ro.a6_costum.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a6_costum.model.Costume;

@Dao
public interface CostumeDao {
    @Query("SELECT * FROM costumes")
    List<Costume> getAll();

    @Insert
    long insert(Costume costume);

    @Update
    int update(Costume costume);

    @Delete
    int delete(Costume costume);
}
