package eu.ase.ro.a1_session.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "sessions")
public class Session implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private Date date;
    private String speaker;
    private int duration;
    private String room;

    public Session() {
    }

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
}
