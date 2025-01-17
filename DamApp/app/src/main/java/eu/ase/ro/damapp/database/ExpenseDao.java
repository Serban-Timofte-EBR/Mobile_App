package eu.ase.ro.damapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.damapp.model.Expense;

@Dao
public interface ExpenseDao {

    @Query("select * from expenses")
    List<Expense> getAll();

    @Insert
    long insert(Expense expense);

    @Update
    int update(Expense expense);

    @Delete
    int delete(Expense expense);
}
