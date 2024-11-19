package eu.ase.ro.student.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Student  implements Parcelable {
    private String name;
    private Integer age;
    private String faculty;
    private String frequency;

    public Student(String name, Integer age, String faculty, String frequency) {
        this.name = name;
        this.age = age;
        this.faculty = faculty;
        this.frequency = frequency;
    }

    public Student() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", faculty='" + faculty + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(faculty);
        dest.writeString(frequency);
    }

    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
        faculty = in.readString();
        frequency = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
