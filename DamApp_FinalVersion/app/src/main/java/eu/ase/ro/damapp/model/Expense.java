package eu.ase.ro.damapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "expenses")
public class Expense implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Date date;
    private double amount;
    private String category;
    private String description;

    public Expense(long id, Date date, double amount, String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    @Ignore
    public Expense(Date date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    @Ignore
    public Expense(Parcel parcel) {
        id = parcel.readLong();
        date = DateConverter.toDate(parcel.readString());
        amount = parcel.readDouble();
        category = parcel.readString();
        description = parcel.readString();
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return date + ": " + amount + ", " + category + " --> " + description;
    }

    public final static Creator<Expense> CREATOR = new Creator<>() {
        @Override
        public Expense createFromParcel(Parcel parcel) {
            return new Expense(parcel);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(DateConverter.fromDate(date));
        parcel.writeDouble(amount);
        parcel.writeString(category);
        parcel.writeString(description);
    }
}
