package eu.ase.ro.a9_jurnal;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "jurnals")
public class Jurnal {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int expenses;
    private Date date;
    private String destination;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jurnal jurnal = (Jurnal) o;
        return expenses == jurnal.expenses && Objects.equals(date, jurnal.date) && Objects.equals(destination, jurnal.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenses, date, destination);
    }

    @Override
    public String toString() {
        return "Jurnal{" +
                "id=" + id +
                ", expenses=" + expenses +
                ", date=" + date +
                ", destination='" + destination + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Ignore
    public Jurnal(int expenses, Date date, String destination) {
        this.expenses = expenses;
        this.date = date;
        this.destination = destination;
    }

    public Jurnal(long id, int expenses, Date date, String destination) {
        this.id = id;
        this.expenses = expenses;
        this.date = date;
        this.destination = destination;
    }
}
