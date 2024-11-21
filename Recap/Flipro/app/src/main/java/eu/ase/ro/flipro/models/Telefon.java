package eu.ase.ro.flipro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Telefon implements Parcelable {
    private String brand;
    private String model;
    private Integer procentBaterie;

    public Telefon(String brand, String model, Integer procentBaterie) {
        this.brand = brand;
        this.model = model;
        this.procentBaterie = procentBaterie;
    }

    protected Telefon(Parcel in) {
        brand = in.readString();
        model = in.readString();
        procentBaterie = in.readInt();
    }

    public static final Creator<Telefon> CREATOR = new Creator<Telefon>() {
        @Override
        public Telefon createFromParcel(Parcel in) {
            return new Telefon(in);
        }

        @Override
        public Telefon[] newArray(int size) {
            return new Telefon[size];
        }
    };

    @Override
    public String toString() {
        return "Telefon{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", procentBaterie=" + procentBaterie +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProcentBaterie() {
        return procentBaterie;
    }

    public void setProcentBaterie(Integer procentBaterie) {
        this.procentBaterie = procentBaterie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeInt(procentBaterie);
    }
}
