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
    @Query("SELECT * FROM expenses")
    List<Expense> getAll();

    @Insert()
    long insert(Expense expense);

    @Update
    int update(Expense expense);    // int din return inseamna cate randuri au fost afectate

    @Delete
    int delete(Expense expense);
}
