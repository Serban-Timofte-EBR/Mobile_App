package eu.ase.ro.a8_workshop;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "workshops")
public class Workshop {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String oraganizer;
    private Location location;
    private float totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workshop workshop = (Workshop) o;
        return Float.compare(totalPrice, workshop.totalPrice) == 0 && Objects.equals(oraganizer, workshop.oraganizer) && location == workshop.location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oraganizer, location, totalPrice);
    }

    @Override
    public String toString() {
        return "Workshop{" +
                "id=" + id +
                ", oraganizer='" + oraganizer + '\'' +
                ", location=" + location +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOraganizer() {
        return oraganizer;
    }

    public void setOraganizer(String oraganizer) {
        this.oraganizer = oraganizer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Workshop(String oraganizer, Location location, float totalPrice) {
        this.oraganizer = oraganizer;
        this.location = location;
        this.totalPrice = totalPrice;
    }

    public Workshop(long id, String oraganizer, Location location, float totalPrice) {
        this.id = id;
        this.oraganizer = oraganizer;
        this.location = location;
        this.totalPrice = totalPrice;
    }

    public Workshop() {
    }
}
