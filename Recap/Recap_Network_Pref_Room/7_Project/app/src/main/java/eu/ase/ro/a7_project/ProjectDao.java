package eu.ase.ro.a7_project;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM projects")
    List<Project> getAll();

    @Insert
    long insert(Project project);

    @Update
    int update(Project project);

    @Delete
    int delete(Project project);
}
