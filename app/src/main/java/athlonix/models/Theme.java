package athlonix.models;

import com.google.gson.annotations.SerializedName;

public class Theme {
    @SerializedName("name")
    private String name;
    @SerializedName("size")
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
