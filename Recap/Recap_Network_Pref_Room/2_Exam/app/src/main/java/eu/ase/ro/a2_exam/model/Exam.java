package eu.ase.ro.a2_exam.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "exams")
public class Exam implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private Date date;
    private Course course;
    private String classRoom;
    private String supervisor;

    public Exam() {
    }

    public Exam(Date date, Course course, String classRoom, String supervisor) {
        this.date = date;
        this.course = course;
        this.classRoom = classRoom;
        this.supervisor = supervisor;
    }

    public Exam(long id, Date date, Course course, String classRoom, String supervisor) {
        this.id = id;
        this.date = date;
        this.course = course;
        this.classRoom = classRoom;
        this.supervisor = supervisor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "date=" + date +
                ", course=" + course +
                ", classRoom='" + classRoom + '\'' +
                ", supervisor='" + supervisor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(date, exam.date) && course == exam.course && Objects.equals(classRoom, exam.classRoom) && Objects.equals(supervisor, exam.supervisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, course, classRoom, supervisor);
    }
}
