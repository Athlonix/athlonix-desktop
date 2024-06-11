package athlonix.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Task {
    @SerializedName("id")
    private int id;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("priority")
    private String priority;
    @SerializedName("id_activity_exception")
    private int idActivityException;
    @SerializedName("description")
    private String description;
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String status;
    @SerializedName("occurence")
    private Occurrence occurence;
    @SerializedName("employee")
    private TeamMember employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getIdActivityException() {
        return idActivityException;
    }

    public void setIdActivityException(int idActivityException) {
        this.idActivityException = idActivityException;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Occurrence getOccurence() {
        return occurence;
    }

    public void setOccurence(Occurrence occurence) {
        this.occurence = occurence;
    }

    public TeamMember getEmployee() {
        return employee;
    }

    public void setEmployee(TeamMember employee) {
        this.employee = employee;
    }
}
