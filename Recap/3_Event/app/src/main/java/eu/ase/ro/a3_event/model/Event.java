package eu.ase.ro.a3_event.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "events")
public class Event implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private String title;
    private Integer nrOfPersons;

    @Ignore
    public Event() {
    }

    @Ignore
    public Event(Date date, String title, Integer nrOfPersons) {
        this.date = date;
        this.title = title;
        this.nrOfPersons = nrOfPersons;
    }

    public Event(long id, Date date, String title, Integer nrOfPersons) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.nrOfPersons = nrOfPersons;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNrOfPersons() {
        return nrOfPersons;
    }

    public void setNrOfPersons(Integer nrOfPersons) {
        this.nrOfPersons = nrOfPersons;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", nrOfPersons=" + nrOfPersons +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(date, event.date) && Objects.equals(title, event.title) && Objects.equals(nrOfPersons, event.nrOfPersons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, title, nrOfPersons);
    }
}
