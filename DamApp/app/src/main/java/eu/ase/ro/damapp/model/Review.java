package eu.ase.ro.damapp.model;

import java.util.UUID;

public class Review {
    private String id;
    private float star;
    private String description;

    public Review(float star, String description) {
        this.id = UUID.randomUUID().toString();
        this.star = star;
        this.description = description;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Review: " + description + " (" + star + ")";
    }
}
