package eu.ase.ro.petmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Pet implements Parcelable {
    private String petName;
    private String petOwner;
    private Date bornDate;
    private Integer age;

    public Pet(String petName, String petOwner, Date bornDate, Integer age) {
        this.petName = petName;
        this.petOwner = petOwner;
        this.bornDate = bornDate;
        this.age = age;
    }

    public Pet() {
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petName='" + petName + '\'' +
                ", petOwner='" + petOwner + '\'' +
                ", bornDate=" + bornDate +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.petName);
        dest.writeString(this.petOwner);
        dest.writeString(DateConverter.fromDate(this.bornDate));
        dest.writeInt(this.age);
    }

    protected Pet(Parcel in) {
        petName = in.readString();
        petOwner = in.readString();
        bornDate = DateConverter.toDate(in.readString());
        age = in.readInt();
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
}
