package eu.ase.ro.a4_pacient.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "patients")
public class Patient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String hospital;
    private Integer numberOfConsultations;

    @Ignore
    public Patient() {
    }

    @Ignore
    public Patient(String name, String hospital, Integer numberOfConsultations) {
        this.name = name;
        this.hospital = hospital;
        this.numberOfConsultations = numberOfConsultations;
    }

    public Patient(long id, String name, String hospital, Integer numberOfConsultations) {
        this.id = id;
        this.name = name;
        this.hospital = hospital;
        this.numberOfConsultations = numberOfConsultations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Integer getNumberOfConsultations() {
        return numberOfConsultations;
    }

    public void setNumberOfConsultations(Integer numberOfConsultations) {
        this.numberOfConsultations = numberOfConsultations;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hospital='" + hospital + '\'' +
                ", numberOfConsultations=" + numberOfConsultations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(name, patient.name) && Objects.equals(hospital, patient.hospital) && Objects.equals(numberOfConsultations, patient.numberOfConsultations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hospital, numberOfConsultations);
    }
}
