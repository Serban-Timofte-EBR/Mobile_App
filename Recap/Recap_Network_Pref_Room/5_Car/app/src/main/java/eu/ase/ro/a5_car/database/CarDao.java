package eu.ase.ro.a5_car.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a5_car.model.Car;

@Dao
public interface CarDao {
    @Query("SELECT * FROM cars")
    List<Car> getAll();

    @Insert
    long insert(Car car);

    @Update
    int update(Car car);

    @Delete
    int delete(Car car);
}
