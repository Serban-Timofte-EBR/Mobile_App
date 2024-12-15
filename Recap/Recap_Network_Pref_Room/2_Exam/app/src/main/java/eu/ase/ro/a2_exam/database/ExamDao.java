package eu.ase.ro.a2_exam.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.ro.a2_exam.model.Exam;

@Dao
public interface ExamDao {
    @Query("SELECT * FROM exams")
    List<Exam> getAll();

    @Insert
    long insert(Exam exam);

    @Update
    int update(Exam exam);

    @Delete
    int delete(Exam exam);
}
