package athlonix.models;

import com.google.gson.annotations.Expose;

public class Sport {
    @Expose
    private int id;
    @Expose
    private String name;


    public Sport(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
