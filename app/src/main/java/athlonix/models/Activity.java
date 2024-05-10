package athlonix.models;

import java.util.Date;
import java.util.List;

public class Activity {

    private int id;
    private int maxParticipants;
    private String name;
    private int idSport;

    public Activity(int id, int maxParticipants, String name, int idSport, int idAddress, List<String> days, Date startDate, Date endDate, int interval, String description, int minParticipants, String recurrence) {
        this.id = id;
        this.maxParticipants = maxParticipants;
        this.name = name;
        this.idSport = idSport;
        this.idAddress = idAddress;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.description = description;
        this.minParticipants = minParticipants;
        this.recurrence = recurrence;
    }

    private int idAddress;
    private List<String> days;
    private Date startDate;
    private Date endDate;
    private int interval;
    private String description;
    private int minParticipants;
    private String recurrence;


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

    public int getIdSport() {
        return idSport;
    }

    public void setIdSport(int idSport) {
        this.idSport = idSport;
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
