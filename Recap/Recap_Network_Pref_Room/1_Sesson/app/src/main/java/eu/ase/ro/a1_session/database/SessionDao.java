package eu.ase.ro.a1_session.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a1_session.model.Session;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM sessions")
    List<Session> getAll();

    @Insert
    long insert(Session session);

    @Update
    int update(Session session);

    @Delete
    int delete(Session session);
}
