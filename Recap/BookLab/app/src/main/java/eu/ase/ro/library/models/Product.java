package eu.ase.ro.library.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    public enum Category {
        ELECTRONICS,
        APPLIANCES,
        FURNITURE
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    private Category category;
    private Double price;
    @ColumnInfo(name = "product_name")
    private String name;

    public Product(long id, Category category, Double price, String name) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.name = name;
    }

    @Ignore
    public Product(Category category, Double price, String name) {
        this.category = category;
        this.price = price;
        this.name = name;
    }

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
