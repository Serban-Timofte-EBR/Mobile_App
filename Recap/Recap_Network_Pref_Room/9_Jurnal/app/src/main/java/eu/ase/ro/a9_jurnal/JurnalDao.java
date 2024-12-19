package eu.ase.ro.a9_jurnal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JurnalDao {
    @Query("SELECT * FROM jurnals")
    List<Jurnal> getAll();

    @Insert
    long insert(Jurnal jurnal);

    @Delete
    int delete(Jurnal jurnal);
}
