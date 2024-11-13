package eu.ase.ro.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Task implements Parcelable {
    private Date deadline;
    private String username;
    private String description;
    private String category;
    private Double pricePerHour;

    public Task(Date deadline, String username, String description, String category, Double pricePerHour) {
        this.deadline = deadline;
        this.username = username;
        this.description = description;
        this.category = category;
        this.pricePerHour = pricePerHour;
    }

    public Task() {
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Task{" +
                "deadline=" + deadline +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(DateConverter.fromDate(deadline));
        dest.writeString(username);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeDouble(pricePerHour);
    }

    protected Task(Parcel in) {
        deadline = DateConverter.toDate(in.readString());
        username = in.readString();
        description = in.readString();
        category = in.readString();
        pricePerHour = in.readDouble();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}