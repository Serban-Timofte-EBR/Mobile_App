package eu.ase.ro.a7_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "projects")
public class Project {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String company;
    private float cost;
    private String contactPerson;

    public Project(long id, String company, float cost, String contactPerson) {
        this.id = id;
        this.company = company;
        this.cost = cost;
        this.contactPerson = contactPerson;
    }

    public Project(String company, float cost, String contactPerson) {
        this.company = company;
        this.cost = cost;
        this.contactPerson = contactPerson;
    }

    public Project() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Float.compare(cost, project.cost) == 0 && Objects.equals(company, project.company) && Objects.equals(contactPerson, project.contactPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, cost, contactPerson);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", cost=" + cost +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
