package eu.ase.ro.consultationmanagement.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Consultation implements Parcelable {
    private Date date;
    private String patient;
    private String diagnostic;

    public Consultation(Date date, String patient, String diagnostic) {
        this.date = date;
        this.patient = patient;
        this.diagnostic = diagnostic;
    }

    protected Consultation(Parcel in) {
        date = DateConverter.toDate(in.readString());
        patient = in.readString();
        diagnostic = in.readString();
    }

    public static final Creator<Consultation> CREATOR = new Creator<Consultation>() {
        @Override
        public Consultation createFromParcel(Parcel in) {
            return new Consultation(in);
        }

        @Override
        public Consultation[] newArray(int size) {
            return new Consultation[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "date=" + date +
                ", patient='" + patient + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(DateConverter.fromDate(this.date));
        dest.writeString(this.patient);
        dest.writeString(this.diagnostic);
    }
}
