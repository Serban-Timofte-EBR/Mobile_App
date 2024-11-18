package eu.ase.ro.magazinhuse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class HusaTelefon implements Parcelable {
    private String material;
    private Double lungime;
    private String modelTelefon;

    public HusaTelefon() {
    }

    public HusaTelefon(String material, Double lungime, String modelTelefon) {
        this.material = material;
        this.lungime = lungime;
        this.modelTelefon = modelTelefon;
    }



    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getLungime() {
        return lungime;
    }

    public void setLungime(Double lungime) {
        this.lungime = lungime;
    }

    public String getModelTelefon() {
        return modelTelefon;
    }

    public void setModelTelefon(String modelTelefon) {
        this.modelTelefon = modelTelefon;
    }

    @Override
    public String toString() {
        return "HusaTelefon{" +
                "material='" + material + '\'' +
                ", lungime=" + lungime +
                ", modelTelefon='" + modelTelefon + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(material);
        dest.writeDouble(lungime);
        dest.writeString(modelTelefon);
    }

    protected HusaTelefon(Parcel in) {
        material = in.readString();
        lungime = in.readDouble();
        modelTelefon = in.readString();
    }

    public static final Creator<HusaTelefon> CREATOR = new Creator<HusaTelefon>() {
        @Override
        public HusaTelefon createFromParcel(Parcel in) {
            return new HusaTelefon(in);
        }

        @Override
        public HusaTelefon[] newArray(int size) {
            return new HusaTelefon[size];
        }
    };
}
