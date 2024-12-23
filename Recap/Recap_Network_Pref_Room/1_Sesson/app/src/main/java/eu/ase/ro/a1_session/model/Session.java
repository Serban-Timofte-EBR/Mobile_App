package eu.ase.ro.a1_session.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "sessions")
public class Session implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private Date date;
    private String speaker;
    private int duration;
    private String room;

    @Ignore
    public Session() {
    }

    @Ignore
    public Session(String title, Date date, String speaker, int duration, String room) {
        this.title = title;
        this.date = date;
        this.speaker = speaker;
        this.duration = duration;
        this.room = room;
    }

    public Session(long id, String title, Date date, String speaker, int duration, String room) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.speaker = speaker;
        this.duration = duration;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", speaker='" + speaker + '\'' +
                ", duration=" + duration +
                ", room='" + room + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return duration == session.duration && Objects.equals(title, session.title) && Objects.equals(date, session.date) && Objects.equals(speaker, session.speaker) && Objects.equals(room, session.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, speaker, duration, room);
    }
}
