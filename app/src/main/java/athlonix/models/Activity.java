package athlonix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Activity {

    @Expose
    private int id;
    @Expose
    @SerializedName("max_participants")
    private int maxParticipants;
    @Expose
    private String name;
    @Expose
    @SerializedName("sport")
    private Sport sport;
    @Expose
    @SerializedName("id_Address")
    private int idAddress;
    @Expose
    private List<String> days;
    @Expose
    @SerializedName("start_date")
    private Date startDate;
    @Expose
    @SerializedName("end_date")
    private Date endDate;
    @Expose
    private int interval;
    @Expose
    private String description;
    @Expose
    @SerializedName("min_participants")
    private int minParticipants;
    @Expose
    private String recurrence;

    public Activity(int id, int maxParticipants, String name,Sport sport, int idAddress, List<String> days, Date startDate, Date endDate, int interval, String description, int minParticipants, String recurrence) {
        this.id = id;
        this.maxParticipants = maxParticipants;
        this.name = name;
        this.idAddress = idAddress;
        this.sport = sport;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.description = description;
        this.minParticipants = minParticipants;
        this.recurrence = recurrence;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

}
