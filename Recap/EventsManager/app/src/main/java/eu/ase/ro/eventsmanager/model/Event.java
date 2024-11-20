package eu.ase.ro.eventsmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Event implements Parcelable {
    private String contactPerson;
    private String location;
    private String eventType;

    public Event(String contactPerson, String location, String eventType) {
        this.contactPerson = contactPerson;
        this.location = location;
        this.eventType = eventType;
    }

    protected Event(Parcel in) {
        contactPerson = in.readString();
        location = in.readString();
        eventType = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public String toString() {
        return "Event{" +
                "contactPerson='" + contactPerson + '\'' +
                ", location='" + location + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.contactPerson);
        dest.writeString(this.location);
        dest.writeString(this.eventType);
    }
}
