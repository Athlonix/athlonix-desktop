package athlonix.models;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("days_of_week")
    private int id;
    @SerializedName("created_at")
    private String createdAt;
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
    @SerializedName("employee")
    private TeamMember employee;
}
