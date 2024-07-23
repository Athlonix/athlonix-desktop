package athlonix.models;

import java.util.Date;

public class ActivityOccurence {
    private String id_exception;
    private Date date;

    public String getId_exception() {
        return id_exception;
    }

    public void setId_exception(String id_exception) {
        this.id_exception = id_exception;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
