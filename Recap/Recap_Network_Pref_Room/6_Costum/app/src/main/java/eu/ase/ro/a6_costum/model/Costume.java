package eu.ase.ro.a6_costum.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "costumes")
public class Costume {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String manufacter;
    private Size size;
    private Float price;

    public Costume() {
    }

    public Costume(String manufacter, Size size, Float price) {
        this.manufacter = manufacter;
        this.size = size;
        this.price = price;
    }

    public Costume(Long id, String manufacter, Size size, Float price) {
        this.id = id;
        this.manufacter = manufacter;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacter() {
        return manufacter;
    }

    public void setManufacter(String manufacter) {
        this.manufacter = manufacter;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Costume{" +
                "id=" + id +
                ", manufacter='" + manufacter + '\'' +
                ", size=" + size +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Costume costume = (Costume) o;
        return Objects.equals(manufacter, costume.manufacter) && size == costume.size && Objects.equals(price, costume.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacter, size, price);
    }
}
