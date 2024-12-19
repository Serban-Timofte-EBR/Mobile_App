package eu.ase.ro.a8_workshop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkshopDao {
    @Query("SELECT * FROM workshops")
    List<Workshop> getAll();

    @Insert
    long insert(Workshop workshop);

    @Update
    int update(Workshop workshop);

    @Delete
    int delete(Workshop workshop);
}
