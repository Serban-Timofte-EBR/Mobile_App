package eu.ase.ro.labex;

import java.util.Date;

public class Exam {
    private Course course;
    private Date date;
    private String classroom;
    private String supervisor;

    public Exam(Course course, Date date, String classroom, String supervisor) {
        this.course = course;
        this.date = date;
        this.classroom = classroom;
        this.supervisor = supervisor;
    }

    public Exam() {
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "course=" + course +
                ", date=" + date +
                ", classroom='" + classroom + '\'' +
                ", supervisor='" + supervisor + '\'' +
                '}';
    }
}