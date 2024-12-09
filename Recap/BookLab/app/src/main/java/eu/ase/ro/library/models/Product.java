package eu.ase.ro.library.models;

public class Product {
    public enum Category {
        ELECTRONICS,
        APPLIANCES,
        FURNITURE
    }

    private Category category;
    private Double price;
    private String name;

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

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
