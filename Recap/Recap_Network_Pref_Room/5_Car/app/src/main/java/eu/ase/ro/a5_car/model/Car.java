package eu.ase.ro.a5_car.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "cars")
public class Car {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String brand;
    private String model;
    private Integer fabricationYear;

    public Car() {
    }

    public Car(String brand, String model, Integer fabricationYear) {
        this.brand = brand;
        this.model = model;
        this.fabricationYear = fabricationYear;
    }

    public Car(long id, String brand, String model, Integer fabricationYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.fabricationYear = fabricationYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getFabricationYear() {
        return fabricationYear;
    }

    public void setFabricationYear(Integer fabricationYear) {
        this.fabricationYear = fabricationYear;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fabricationYear=" + fabricationYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(fabricationYear, car.fabricationYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, fabricationYear);
    }
}
