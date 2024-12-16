package eu.ase.ro.a3_event.database;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a3_event.model.Event;
import eu.ase.ro.a3_event.network.Callback;

@Dao
public interface EventsDao {
    @Query("SELECT * FROM events")
    List<Event> getAll();

    @Insert
    long insert(Event event);

    @Update
    int update(Event event);

    @Delete
    int delete(Event event);

}
