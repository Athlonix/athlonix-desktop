package athlonix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Activity {

    @Expose
    private int id;
    @Expose
    @SerializedName("min_participants")
    private int minParticipants;
    @Expose
    @SerializedName("max_participants")
    private int maxParticipants;
    @Expose
    private String name;
    @Expose
    @SerializedName("sport")
    private Sport sport;
    @Expose
    @SerializedName("id_address")
    private int idAddress;
    @Expose
    @SerializedName("days_of_week")
    private List<String> days;
    @Expose
    @SerializedName("start_date")
    private Date startDate;
    @Expose
    @SerializedName("end_date")
    private Date endDate;
    @Expose
    @SerializedName("start_time")
    private String startTime;
    @Expose
    @SerializedName("end_time")
    private String endTime;
    @Expose
    @SerializedName("frequency")
    private String frequency;
    @Expose
    private String description;

    public Activity(int id, int minParticipants, int maxParticipants, String name, Sport sport, int idAddress, List<String> days, Date startDate, Date endDate, String startTime, String endTime, String frequency, String description) {
        this.id = id;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.name = name;
        this.sport = sport;
        this.idAddress = idAddress;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
