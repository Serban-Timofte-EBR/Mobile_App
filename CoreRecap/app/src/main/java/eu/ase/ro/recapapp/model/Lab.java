package eu.ase.ro.recapapp.model;

import java.io.Serializable;
import java.util.Date;

public class Lab implements Serializable {
    private long id;
    private Date labDate;
    private Integer classNumber;
    private String labName;

    public Lab(long id, Date labDate, Integer classNumber, String labName) {
        this.id = id;
        this.labDate = labDate;
        this.classNumber = classNumber;
        this.labName = labName;
    }

    public Lab(Date labDate, Integer classNumber, String labName) {
        this.labDate = labDate;
        this.classNumber = classNumber;
        this.labName = labName;
    }

    public Lab() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLabDate() {
        return labDate;
    }

    public void setLabDate(Date labDate) {
        this.labDate = labDate;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "id=" + id +
                ", labDate=" + labDate +
                ", classNumber=" + classNumber +
                ", labName='" + labName + '\'' +
                '}';
    }
}
