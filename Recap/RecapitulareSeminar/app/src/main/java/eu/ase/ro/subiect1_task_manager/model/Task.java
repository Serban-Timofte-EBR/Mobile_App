package eu.ase.ro.subiect1_task_manager.model;

import java.util.Date;

public class Task {
    private Date deadline;
    private String username;
    private String description;
    private String category;
    private Double pricePerHour;

    public Task(Date deadline, String username, String description, String category, Double pricePerHour) {
        this.deadline = deadline;
        this.username = username;
        this.description = description;
        this.category = category;
        this.pricePerHour = pricePerHour;
    }

    public Task() {
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Task{" +
                "deadline=" + deadline +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
